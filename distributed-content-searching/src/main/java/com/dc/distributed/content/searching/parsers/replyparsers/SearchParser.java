package com.dc.distributed.content.searching.parsers.replyparsers;

import com.dc.distributed.content.searching.commands.SearchRequest;

import java.net.InetAddress;

public class SearchParser implements ReplyParser {

    @Override
    public SearchRequest parse(String[] commandParts, InetAddress requesterAddr, int requesterPort) {
        // length SER IP port file_name hops
        // first 2 elements are metadata
        String issuerIp = commandParts[2];
        int issuerPort = getInt(commandParts[3]);
        String fileName = commandParts[4].replace("\\ ", " ");
        int hopCount = getInt(commandParts[5]);
        return new SearchRequest(issuerIp, issuerPort, requesterAddr.getHostAddress(), requesterPort, fileName, hopCount);
    }
}
