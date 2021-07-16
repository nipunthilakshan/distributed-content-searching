package com.example.formicarymrt.client.parsers.requestparsers;

import com.example.formicarymrt.client.commands.UnregisterRequest;

public class UnRegRequestParser implements RequestParser<UnregisterRequest>{
    @Override
    public String parse(UnregisterRequest command) {
        return String.format("0000 %s %s %s %s", command.getCommandType(), command.getIssuerIp(), command.getIssuerPort(), command.getUsername());
    }
}
