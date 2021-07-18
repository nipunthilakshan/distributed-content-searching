package com.dc.distributed.content.searching.commands;

import com.dc.distributed.content.searching.Neighbour;
import lombok.Getter;

import java.util.List;

public @Getter
class RegisterReply extends AbstractCommand {

    public static final String REGOK = "REGOK";
    private final List<Neighbour> neighbours;

    public RegisterReply(String issuerIp, int issuerPort, String otherPartyIp, int otherPartyPort, List<Neighbour> neighbours) {

        super(issuerIp, issuerPort, otherPartyIp, otherPartyPort);
        this.neighbours = neighbours;
    }

    @Override
    public String getCommandType() {

        return REGOK;
    }
}
