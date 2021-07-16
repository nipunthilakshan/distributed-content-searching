package com.example.formicarymrt.client.commands;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class JoinRequest extends AbstractCommand {

    public static final String JOIN = "JOIN";

    public JoinRequest(String issuerIp, int issuerPort, String otherPartyIp, int otherPartyPort) {
        super(issuerIp, issuerPort, otherPartyIp, otherPartyPort);
    }

    @Override
    public String getCommandType() {
        return JOIN;
    }
}