package com.example.formicarymrt.client.parsers.requestparsers;

import com.example.formicarymrt.client.commands.JoinReply;

public class JoinOkParser implements RequestParser<JoinReply> {
    @Override
    public String parse(JoinReply command) {
        String statusCode = command.isJoinSuccess() ? "0" : "9999";
        return String.format("0000 %s %s", command.getCommandType(), statusCode);
    }
}
