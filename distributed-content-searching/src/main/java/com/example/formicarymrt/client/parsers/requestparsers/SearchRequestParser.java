package com.example.formicarymrt.client.parsers.requestparsers;

import com.example.formicarymrt.client.commands.SearchRequest;

public class SearchRequestParser implements RequestParser<SearchRequest> {

    @Override
    public String parse(SearchRequest command) {
        // length SER IP port file_name hops
        String fileName = command.getFileName();
        String escapedName = fileName.replace(" ", "\\ ");
        return String.format("0000 %s %s %s %s %s", command.getCommandType(), command.getIssuerIp(), command.getIssuerPort(), escapedName, command.getHopCount());
    }
}
