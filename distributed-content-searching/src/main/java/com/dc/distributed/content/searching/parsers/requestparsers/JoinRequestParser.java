package com.dc.distributed.content.searching.parsers.requestparsers;

import com.dc.distributed.content.searching.commands.JoinRequest;

public class JoinRequestParser implements RequestParser<JoinRequest>
{
    @Override
    public String parse( JoinRequest command )
    {
        return String.format( "0000 %s %s %s", command.getCommandType(), command.getIssuerIp(), command.getIssuerPort());
    }
}