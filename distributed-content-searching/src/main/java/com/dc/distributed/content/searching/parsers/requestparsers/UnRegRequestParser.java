package com.dc.distributed.content.searching.parsers.requestparsers;

import com.dc.distributed.content.searching.commands.UnregisterRequest;

public class UnRegRequestParser implements RequestParser<UnregisterRequest> {

    @Override
    public String parse(UnregisterRequest command) {

        return String.format("0000 %s %s %s %s", command.getCommandType(), command.getIssuerIp(), command.getIssuerPort(), command.getUsername());
    }
}
