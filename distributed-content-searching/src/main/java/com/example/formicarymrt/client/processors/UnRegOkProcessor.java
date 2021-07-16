package com.example.formicarymrt.client.processors;

import com.example.formicarymrt.client.ApplicationState;
import com.example.formicarymrt.client.Bootstrap;
import com.example.formicarymrt.client.CommandSender;
import com.example.formicarymrt.client.commands.LeaveRequest;
import com.example.formicarymrt.client.commands.UnregisterReply;
import com.example.formicarymrt.client.models.InputParameters;
import com.example.formicarymrt.client.parsers.replyparsers.RegReplyParser;
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
