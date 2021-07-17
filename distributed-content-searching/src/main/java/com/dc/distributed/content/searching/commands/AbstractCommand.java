package com.dc.distributed.content.searching.commands;

import lombok.Getter;

@Getter
public abstract class AbstractCommand implements Command {

    // original requester JOof a command
    String issuerIp;
    int issuerPort;
    String otherPartyIp;
    int otherPartyPort;

    public AbstractCommand(String issuerIp, int issuerPort, String otherPartyIp, int otherPartyPort) {
        this.issuerIp = issuerIp;
        this.issuerPort = issuerPort;
        this.otherPartyIp = otherPartyIp;
        this.otherPartyPort = otherPartyPort;
    }
}
