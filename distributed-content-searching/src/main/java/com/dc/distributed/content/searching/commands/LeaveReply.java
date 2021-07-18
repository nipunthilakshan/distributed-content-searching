package com.dc.distributed.content.searching.commands;

import lombok.Getter;

@Getter
public class LeaveReply extends AbstractCommand {

    public static final String LEAVEOK = "LEAVEOK";
    private final boolean isJoinSuccess;

    public LeaveReply(String issuerIp, int issuerPort, String otherPartyIp, int otherPartyPort, boolean isJoinSuccess) {

        super(issuerIp, issuerPort, otherPartyIp, otherPartyPort);
        this.isJoinSuccess = isJoinSuccess;
    }

    @Override
    public String getCommandType() {

        return LEAVEOK;
    }
}