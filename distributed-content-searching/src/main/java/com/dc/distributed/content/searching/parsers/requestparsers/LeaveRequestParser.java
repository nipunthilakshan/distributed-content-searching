package com.dc.distributed.content.searching.parsers.requestparsers;

import com.dc.distributed.content.searching.commands.LeaveRequest;

public class LeaveRequestParser implements RequestParser<LeaveRequest> {

    @Override
    public String parse(LeaveRequest command) {

        return String.format("0000 %s %s %s", command.getCommandType(), command.getIssuerIp(), command.getIssuerPort());
    }
}
