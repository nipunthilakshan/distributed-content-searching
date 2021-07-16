package com.example.formicarymrt.client.commands;

import com.example.formicarymrt.client.Neighbour;
import lombok.Getter;

import java.util.List;

public @Getter
class RegisterReply extends AbstractCommand {

    public static final String REGOK = "REGOK";
    private List<Neighbour> neighbours;

    public RegisterReply(String issuerIp, int issuerPort, String otherPartyIp, int otherPartyPort, List<Neighbour> neighbours) {
        super(issuerIp, issuerPort, otherPartyIp, otherPartyPort);
        this.neighbours = neighbours;
    }

    @Override
    public String getCommandType() {
        return REGOK;
    }
}
