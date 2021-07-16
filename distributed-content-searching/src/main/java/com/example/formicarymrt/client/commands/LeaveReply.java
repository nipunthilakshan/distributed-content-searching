package com.example.formicarymrt.client.commands;

import com.example.formicarymrt.client.Neighbour;
import lombok.Getter;

import java.util.List;

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