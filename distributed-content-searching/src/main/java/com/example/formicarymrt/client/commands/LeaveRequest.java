package com.example.formicarymrt.client.commands;

import lombok.Getter;

public @Getter
class LeaveRequest extends AbstractCommand {

    public static final String LEAVE = "LEAVE";

    public LeaveRequest(String issuerIp, int issuerPort, String otherPartyIp, int otherPartyPort) {
        super(issuerIp, issuerPort, otherPartyIp, otherPartyPort);
    }

    @Override
    public String getCommandType() {
        return LEAVE;
    }
}
