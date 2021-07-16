package com.example.formicarymrt.client.parsers.requestparsers;

import com.example.formicarymrt.client.commands.RegisterRequest;

public class RegRequesterParser implements RequestParser<RegisterRequest> {
    @Override
    public String parse(RegisterRequest command) {
        return String.format("0000 %s %s %s %s", command.getCommandType(), command.getIssuerIp(), command.getIssuerPort(), command.getUsername());
    }
}
