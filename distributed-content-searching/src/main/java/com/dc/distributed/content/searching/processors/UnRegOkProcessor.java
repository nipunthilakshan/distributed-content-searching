package com.dc.distributed.content.searching.processors;

import com.dc.distributed.content.searching.ApplicationState;
import com.dc.distributed.content.searching.Bootstrap;
import com.dc.distributed.content.searching.CommandSender;
import com.dc.distributed.content.searching.commands.LeaveRequest;
import com.dc.distributed.content.searching.commands.UnregisterReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UnRegOkProcessor extends AbstractCommandProcessor<UnregisterReply> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnRegOkProcessor.class);
    @Autowired
    private Bootstrap bootstrap;

    private ApplicationState applicationState;
    private final CommandSender commandSender;

    public UnRegOkProcessor(ApplicationState applicationState, CommandSender commandSender) {
        super(applicationState, commandSender);

        this.applicationState = applicationState;
        this.commandSender = commandSender;
    }

    @Override
    protected void processSynchronously(UnregisterReply message) throws IOException {
        if (message.getStatus() == 9999) {
            System.out.println("____________________ERROR________________________");
        } else if (message.getStatus() == 0) {
//            LOGGER.info("Re-registering");
//            bootstrap.connectToBootstrapServer( InputParameters.getBootstrapAddr(), InputParameters.getBootstrapPort(), InputParameters.getIp(), InputParameters.getPort(), InputParameters.getUsername() );

            applicationState.getNeighbours().forEach(neighbour -> {
                try {
                    commandSender.sendRequest(new LeaveRequest(applicationState.getIpAddress(), applicationState.getPort(), neighbour.getIp(), neighbour.getPort()));
                } catch (IOException e) {
                    LOGGER.error("Sending LEAVE request failed.", e);
                }
            });
        }
    }
}
