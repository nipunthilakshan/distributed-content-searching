package com.dc.distributed.content.searching.commands;

import lombok.Getter;

public @Getter
class RegisterRequest extends AbstractCommand {

    public static final String REG = "REG";
    private String username;

    public RegisterRequest(String issuerIp, int issuerPort, String otherPartyIp, int otherPartyPort, String username) {
        super(issuerIp, issuerPort, otherPartyIp, otherPartyPort);
        this.username = username;
    }

    @Override
    public String getCommandType() {
        return REG;
    }
}
