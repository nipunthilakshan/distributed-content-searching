package com.dc.distributed.content.searching.processors;

import com.dc.distributed.content.searching.ApplicationState;
import com.dc.distributed.content.searching.CommandSender;
import com.dc.distributed.content.searching.commands.LeaveReply;
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
