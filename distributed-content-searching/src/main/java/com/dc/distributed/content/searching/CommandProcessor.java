package com.dc.distributed.content.searching;

import com.dc.distributed.content.searching.commands.*;
import com.dc.distributed.content.searching.models.InputParameters;
import com.dc.distributed.content.searching.processors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CommandProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandProcessor.class);

    private final RegOkProcessor regOkProcessor;
    private final CommandSender commandSender;
    private final SearchOkProcessor searchOkProcessor;
    private final SearchRequestProcessor searchRequestProcessor;
    private final JoinRequestProcessor joinRequestProcessor;
    private final JoinOkProcessor joinOkProcessor;
    private final LeaveRequestProcesssor leaveRequestProcesssor;
    private final LeaveOKProcessor leaveOKProcessor;

    private final ApplicationState applicationState;
    private final UnRegOkProcessor unRegOkProcessor;

    public CommandProcessor(CommandListener commandListener,
                            ApplicationState applicationState,
                            RegOkProcessor regOkProcessor,
                            CommandSender commandSender,
                            SearchOkProcessor searchOkProcessor,
                            SearchRequestProcessor searchRequestProcessor,
                            UnRegOkProcessor unRegOkProcessor,
                            JoinRequestProcessor joinRequestProcessor,
                            JoinOkProcessor joinOkProcessor,
                            LeaveRequestProcesssor leaveRequestProcesssor,
                            LeaveOKProcessor leaveOKProcessor) {

        this.regOkProcessor = regOkProcessor;
        this.commandSender = commandSender;
        this.searchOkProcessor = searchOkProcessor;
        this.searchRequestProcessor = searchRequestProcessor;
        this.joinRequestProcessor = joinRequestProcessor;
        this.joinOkProcessor = joinOkProcessor;
        this.leaveRequestProcesssor = leaveRequestProcesssor;
        this.leaveOKProcessor = leaveOKProcessor;
        commandListener.registerOnCommandListener(this::handleCommand);
        this.applicationState = applicationState;
        this.unRegOkProcessor = unRegOkProcessor;
    }

    public void search(String fileName) {

        try {
            commandSender.sendRequest(new SearchRequest(applicationState.getIpAddress(), applicationState.getPort(), applicationState.getIpAddress(), applicationState.getPort(), fileName, -1));
        }
        catch ( IOException e ) {
            LOGGER.error("Sending SER request failed.", e);
        }
    }

    public void unregister() {

        try {
            commandSender.sendRequest(new UnregisterRequest(InputParameters.getIp(), InputParameters.getPort(), InputParameters.getBootstrapAddr(), InputParameters.getBootstrapPort(), InputParameters.getUsername()));
        } catch (IOException e) {
            LOGGER.error("Sending SER request failed.", e);
        }
    }

    private void handleCommand(AbstractCommand reply) throws IOException {

        System.out.println("command recieved: " + reply.getCommandType());

        switch (reply.getCommandType()) {
            case RegisterReply.REGOK:
                regOkProcessor.process((RegisterReply) reply);
                break;
            case SearchReply.SEROK:
                searchOkProcessor.process(reply);
                break;
            case SearchRequest.SER:
                searchRequestProcessor.process((SearchRequest) reply);
                break;
            case UnregisterReply.UNREGOK:
                unRegOkProcessor.process((UnregisterReply) reply);
                break;
            case JoinRequest.JOIN:
                joinRequestProcessor.process((JoinRequest) reply);
                break;
            case JoinReply.JOIN_OK:
                joinOkProcessor.process((JoinReply) reply);
                break;
            case LeaveRequest.LEAVE:
                leaveRequestProcesssor.process((LeaveRequest) reply);
                break;
            case LeaveReply.LEAVEOK:
                leaveOKProcessor.process((LeaveReply) reply);
                break;
        }
    }
}
