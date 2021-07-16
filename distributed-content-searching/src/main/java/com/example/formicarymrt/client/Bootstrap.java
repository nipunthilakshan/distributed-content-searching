package com.example.formicarymrt.client;

import com.example.formicarymrt.client.commands.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.formicarymrt.client.commands.UnregisterRequest;

import java.io.IOException;

@Service
public class Bootstrap {

    private final CommandSender commandSender;

    public Bootstrap(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    public void connectToBootstrapServer(String ipAddr, int port, String bootstrapAddr, int bootstrapPort, String username) throws IOException {

        RegisterRequest request = new RegisterRequest(ipAddr, port, bootstrapAddr, bootstrapPort, username);
        commandSender.sendRequest(request);
    }

    public void disconnectFromBootstrapServer( String bootstrapAddr, int bootstrapPort, String ipAddr, int port, String username ) throws IOException
    {
        UnregisterRequest unregisterRequest = new UnregisterRequest( bootstrapAddr, bootstrapPort, ipAddr, port, username );
        commandSender.sendRequest( unregisterRequest );
    }
}
