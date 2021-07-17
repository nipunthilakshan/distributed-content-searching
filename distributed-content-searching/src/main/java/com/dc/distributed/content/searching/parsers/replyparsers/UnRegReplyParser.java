package com.dc.distributed.content.searching.parsers.replyparsers;

import com.dc.distributed.content.searching.commands.AbstractCommand;
import com.dc.distributed.content.searching.commands.UnregisterReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

public class UnRegReplyParser implements ReplyParser {
    private static final String ERROR = "ERROR";
    private static final Logger LOGGER = LoggerFactory.getLogger(RegReplyParser.class);

    @Override
    public AbstractCommand parse(String[] commandParts, InetAddress requesterAddr, int requesterPort) {
        UnregisterReply unregisterReply = new UnregisterReply(null, -1, requesterAddr.getHostAddress(), requesterPort);

        if (!(commandParts.length == 2 && ERROR.equals(commandParts[1]))) {
            int status = Integer.parseInt(commandParts[2]);
            unregisterReply.setStatus(status);
        }
        return unregisterReply;
    }
}
