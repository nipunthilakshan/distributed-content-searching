package com.example.formicarymrt.client.processors;

import com.example.formicarymrt.client.Bootstrap;
import com.example.formicarymrt.client.models.InputParameters;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UnregisterProcessor
{
    private final Bootstrap bootstrap;

    public UnregisterProcessor(Bootstrap bootstrap)
    {
        this.bootstrap = bootstrap;
    }

    public void unregister() throws IOException
    {
        bootstrap.disconnectFromBootstrapServer( InputParameters.getBootstrapAddr(), InputParameters.getBootstrapPort(), InputParameters.getIp(), InputParameters.getPort(), InputParameters.getUsername() );
    }
}
