package com.example.formicarymrt.client.processors;

import com.example.formicarymrt.client.ApplicationState;
import com.example.formicarymrt.client.CommandSender;
import com.example.formicarymrt.client.Neighbour;
import com.example.formicarymrt.client.commands.JoinReply;
import com.example.formicarymrt.client.commands.LeaveReply;
import com.example.formicarymrt.client.util.JoinRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LeaveOKProcessor extends AbstractCommandProcessor<LeaveReply> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LeaveOKProcessor.class);

    public LeaveOKProcessor(ApplicationState applicationState, CommandSender commandSender) {
        super(applicationState, commandSender);
    }

    @Override
    protected void processSynchronously(LeaveReply message) throws IOException {
        if (message.isJoinSuccess()) {
            LOGGER.info("Successfully Left {} {}", message.getOtherPartyIp(), message.getOtherPartyPort());
        } else {
            LOGGER.info("Failed Leaving {} {}", message.getOtherPartyIp(), message.getOtherPartyPort());
        }
    }
}
