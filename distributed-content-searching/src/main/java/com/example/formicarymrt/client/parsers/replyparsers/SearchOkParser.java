package com.example.formicarymrt.client.parsers.replyparsers;

import com.example.formicarymrt.client.commands.SearchReply;
import com.example.formicarymrt.client.models.SearchHit;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class SearchOkParser implements ReplyParser {
    @Override
    public SearchReply parse(String[] commandParts, InetAddress requesterAddr, int requesterPort) {
        // length SEROK no_files IP port hops filename1 filename2
        int numberOfFiles = getInt(commandParts[2]);

        if (numberOfFiles > 0) {
            SearchHit searchHit = new SearchHit();
            List<String> matchedFiles = new ArrayList<>();
            searchHit.setFileNames(matchedFiles);

            for (int i = 6; i < commandParts.length; i++) {
                matchedFiles.add(commandParts[i].replace("\\ ", " "));
            }

            String ownerIp = commandParts[3];
            int ownerPort = getInt(commandParts[4]);
            searchHit.setOwnerIp(ownerIp);
            searchHit.setOwnerPort(ownerPort);
            int hopCount = getInt(commandParts[5]);
            searchHit.setHops(hopCount);
            return new SearchReply(ownerIp, ownerPort, requesterAddr.getHostAddress(), requesterPort, searchHit);
        }
        return new SearchReply(null, -1, requesterAddr.getHostAddress(), requesterPort, null);
    }
}
