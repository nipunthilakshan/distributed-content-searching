package com.dc.distributed.content.searching.parsers.replyparsers;

import com.dc.distributed.content.searching.commands.JoinRequest;

import java.io.IOException;
import java.net.InetAddress;

public class JoinResponseParser implements ReplyParser {

    @Override
    public JoinRequest parse(String[] commandParts, InetAddress requesterAddr, int requesterPort) throws IOException {
        // length JOIN IP_address port_no
        // first 2 elements are metadata

        return new JoinRequest(commandParts[2], getInt(commandParts[3]), requesterAddr.getHostAddress(), requesterPort);
    }
}
