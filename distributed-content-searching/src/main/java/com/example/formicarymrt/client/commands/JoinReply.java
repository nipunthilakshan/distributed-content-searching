package com.example.formicarymrt.client.commands;

import lombok.Getter;

@Getter
public class JoinReply extends AbstractCommand {

    public static final String JOIN_OK = "JOINOK";
    private final boolean isJoinSuccess;

    public JoinReply(String issuerIp, int issuerPort, String otherPartyIp, int otherPartyPort, boolean isJoinSuccess) {
        super(issuerIp, issuerPort, otherPartyIp, otherPartyPort);
        this.isJoinSuccess = isJoinSuccess;
    }

    @Override
    public String getCommandType() {
        return JOIN_OK;
    }
}
