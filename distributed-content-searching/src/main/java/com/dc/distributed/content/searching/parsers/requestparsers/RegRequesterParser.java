package com.dc.distributed.content.searching.parsers.requestparsers;

import com.dc.distributed.content.searching.commands.RegisterRequest;

public class RegRequesterParser implements RequestParser<RegisterRequest> {
    @Override
    public String parse(RegisterRequest command) {
        return String.format("0000 %s %s %s %s", command.getCommandType(), command.getIssuerIp(), command.getIssuerPort(), command.getUsername());
    }
}
