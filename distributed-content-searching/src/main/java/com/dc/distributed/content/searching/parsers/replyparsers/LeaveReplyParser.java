package com.dc.distributed.content.searching.parsers.replyparsers;

import com.dc.distributed.content.searching.commands.LeaveReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

public class LeaveReplyParser implements ReplyParser {

    private static final String LEAVE_SUCCESS = "0";
    private static final String LEAVE_FAILED = "9999";

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaveReplyParser.class);

    @Override
    public LeaveReply parse(String[] commandParts, InetAddress requesterAddr, int requesterPort) {

        if (commandParts.length < 2) {
            throw new IllegalArgumentException("Invalid amount of commands received from Leave Reply");
        }

        String statusCode = commandParts[2];

        switch (statusCode) {
            case LEAVE_SUCCESS:
                LOGGER.info("Leave Reply Success received {} {}", requesterAddr.getHostAddress(), requesterPort);
                return new LeaveReply(null, -1, requesterAddr.getHostAddress(), requesterPort, true);
            case LEAVE_FAILED:
                return new LeaveReply(null, -1, requesterAddr.getHostAddress(), requesterPort, false);
            default:
                throw new IllegalArgumentException("Invalid Reply From Neighbour status code = " + statusCode);
        }

    }
}
