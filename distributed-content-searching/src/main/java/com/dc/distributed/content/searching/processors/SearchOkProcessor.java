package com.dc.distributed.content.searching.processors;

import com.dc.distributed.content.searching.ApplicationState;
import com.dc.distributed.content.searching.CommandSender;
import com.dc.distributed.content.searching.commands.AbstractCommand;
import com.dc.distributed.content.searching.commands.SearchReply;
import com.dc.distributed.content.searching.models.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SearchOkProcessor extends AbstractCommandProcessor<AbstractCommand> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchOkProcessor.class);

    public SearchOkProcessor(ApplicationState applicationState, CommandSender commandSender) {
        super(applicationState, commandSender);
    }

    @Override
    protected void processSynchronously(AbstractCommand message) {
        SearchReply reply = (SearchReply) message;

        if (reply.getSearchHit() != null) {
            SearchHit searchHit = ((SearchReply) message).getSearchHit();
            LOGGER.info("Found search result: {}", searchHit);
            boolean hasMatchAlready = getApplicationState().getSearchHits().stream().anyMatch(hit ->
                    hit.getFileNames().equals(searchHit.getFileNames())
                            && hit.getOwnerIp().equals(searchHit.getOwnerIp())
                            && hit.getOwnerPort() == searchHit.getOwnerPort());
            if (!hasMatchAlready) {
                getApplicationState().getSearchHits().add(reply.getSearchHit());
            }
        } else {
            LOGGER.info("Received search failed from {}:{}", message.getOtherPartyIp(), message.getOtherPartyPort());
        }
    }
}
