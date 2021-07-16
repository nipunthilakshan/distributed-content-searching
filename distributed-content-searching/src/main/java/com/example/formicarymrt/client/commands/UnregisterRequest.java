package com.example.formicarymrt.client.commands;

import lombok.Getter;

public @Getter
class UnregisterRequest extends AbstractCommand {

    public static final String UNREG = "UNREG";
    private String username;

    public UnregisterRequest(String clientAddr, int clientPort, String bootstrapAddr, int bootstrapPort, String username) {
        super(clientAddr, clientPort, bootstrapAddr, bootstrapPort);
        this.username = username;
    }

    @Override
    public String getCommandType() {
        return UNREG;
    }
}
