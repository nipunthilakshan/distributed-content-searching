package com.dc.distributed.content.searching.parsers.replyparsers;

import com.dc.distributed.content.searching.commands.JoinReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

//This is to receive the Join response from other node

public class JoinReplyParser implements ReplyParser {

    private static final String JOIN_SUCCESS = "0";
    private static final String JOIN_FAILED = "9999";

    private static final Logger LOGGER = LoggerFactory.getLogger(JoinReplyParser.class);

    @Override
    public JoinReply parse(String[] commandParts, InetAddress requesterAddr, int requesterPort) {

        if (commandParts.length < 2) {
            throw new IllegalArgumentException("Invalid amount of commands received from Join Reply");
        }

        String statusCode = commandParts[2];

        switch (statusCode) {
            case JOIN_SUCCESS:
                LOGGER.info("Join Reply Success received {} {}",requesterAddr.getHostAddress(), requesterPort);
                return new JoinReply(null, -1, requesterAddr.getHostAddress(), requesterPort, true);
            case JOIN_FAILED:
                return new JoinReply(null, -1, requesterAddr.getHostAddress(), requesterPort, false);
            default:
                throw new IllegalArgumentException("Invalid Reply From Neighbour status code = " + statusCode);
        }


    }
}
