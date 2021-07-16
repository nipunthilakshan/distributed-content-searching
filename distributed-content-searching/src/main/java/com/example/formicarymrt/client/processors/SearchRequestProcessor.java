package com.example.formicarymrt.client.processors;

import com.example.formicarymrt.client.ApplicationState;
import com.example.formicarymrt.client.CommandSender;
import com.example.formicarymrt.client.FileRegistry;
import com.example.formicarymrt.client.commands.SearchReply;
import com.example.formicarymrt.client.commands.SearchRequest;
import com.example.formicarymrt.client.models.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SearchRequestProcessor extends AbstractCommandProcessor<SearchRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchRequestProcessor.class);

    @Value("${hopcount}")
    public int hopCount;

    private final FileRegistry fileRegistry;

    public SearchRequestProcessor(ApplicationState applicationState, CommandSender commandSender, FileRegistry fileRegistry) {
        super(applicationState, commandSender);
        this.fileRegistry = fileRegistry;
    }

    @Override
    protected void processSynchronously(SearchRequest reply) {

        String commandId = String.format("%s:%s:%s", reply.getIssuerIp(), reply.getIssuerPort(), reply.getFileName());
        SearchRequest ongoingRequest = getApplicationState().getOngoingRequests().getIfPresent(commandId);

        if (reply.getHopCount() == hopCount) {
            LOGGER.info("{} reached hop limit. Reply with failed message", commandId);
            replyWithSearchFailed(reply);
            return;
        }

        if (ongoingRequest != null) {
            // request already ongoing. no need to handle
            // should let the message die down
            LOGGER.info("{} already in ongoing list. Therefore ignoring.", commandId);
            return;
        }

        // check own registry
        List<String> matchingFiles = fileRegistry.getMatchingFiles(reply.getFileName());

        if (!matchingFiles.isEmpty()) {
            replyWithSerOk(reply, matchingFiles);
        } else {
            getApplicationState().getOngoingRequests().put(commandId, reply);
            passRequestToNeighbours(reply);
        }
    }

    private void replyWithSerOk(SearchRequest reply, List<String> matchingFiles) {
        SearchHit searchHit = new SearchHit();
        searchHit.setFileNames(matchingFiles);
        searchHit.setOwnerIp(getApplicationState().getIpAddress());
        searchHit.setOwnerPort(getApplicationState().getPort());
        searchHit.setHops(reply.getHopCount() + 1);
        SearchReply newReply = new SearchReply(getApplicationState().getIpAddress(), getApplicationState().getDownloadPort(),
                reply.getIssuerIp(), reply.getIssuerPort(), searchHit);
        try {
            getCommandSender().sendRequest(newReply);
        } catch (IOException e) {
            LOGGER.error("Sending SEROK failed.", e);
        }
    }

    private void passRequestToNeighbours(SearchRequest reply) {
        getApplicationState().getNeighbours().forEach(neighbour -> {
            SearchRequest request = new SearchRequest(reply.getIssuerIp(), reply.getIssuerPort(), neighbour.getIp(),
                    neighbour.getPort(), reply.getFileName(), reply.getHopCount() + 1);
            try {
                getCommandSender().sendRequest(request);
            } catch (IOException e) {
                LOGGER.error("Parsing SER to neighbour failed.", e);
            }
        });
    }

    private void replyWithSearchFailed(SearchRequest message) {
        SearchReply newReply = new SearchReply(message.getIssuerIp(), message.getIssuerPort(), message.getOtherPartyIp(),
                message.getOtherPartyPort(), null);

        try {
            getCommandSender().sendRequest(newReply);
        } catch (IOException e) {
            LOGGER.error("Sending \"failed SEROK\" message failed.", e);
        }
    }
}
