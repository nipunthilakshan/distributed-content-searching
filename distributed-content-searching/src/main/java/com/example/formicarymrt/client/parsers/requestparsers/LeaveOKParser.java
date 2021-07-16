package com.example.formicarymrt.client.parsers.requestparsers;


import com.example.formicarymrt.client.commands.LeaveReply;

public class LeaveOKParser implements RequestParser<LeaveReply> {
    @Override
    public String parse(LeaveReply command) {
        String statusCode = command.isJoinSuccess() ? "0" : "9999";
        return String.format("0000 %s %s", command.getCommandType(), statusCode);
    }
}
