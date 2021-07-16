package com.example.formicarymrt.client.parsers.replyparsers;

import com.example.formicarymrt.client.commands.LeaveRequest;

import java.io.IOException;
import java.net.InetAddress;

public class LeaveResponseParser implements ReplyParser {
    @Override
    public LeaveRequest parse(String[] commandParts, InetAddress requesterAddr, int requesterPort) throws IOException {
        // length JOIN IP_address port_no
        // first 2 elements are metadata

        return new LeaveRequest(commandParts[2], getInt(commandParts[3]), requesterAddr.getHostAddress(), requesterPort);
    }
}
