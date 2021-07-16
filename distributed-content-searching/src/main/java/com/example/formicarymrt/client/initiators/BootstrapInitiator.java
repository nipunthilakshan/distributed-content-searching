package com.example.formicarymrt.client.initiators;

import com.example.formicarymrt.client.Bootstrap;
import com.example.formicarymrt.client.CommandListener;
import com.example.formicarymrt.client.models.InputParameters;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Order(1)
@Component
public class BootstrapInitiator implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapInitiator.class);

    private final Bootstrap bootstrap;
    private final CommandListener commandListener;

    @Value("${client.ip}")
    String ip;

    @Value("${client.port}")
    int port;

    @Value("${client.username}")
    String username;

    @Value("${bootstrap.ip}")
    String bootstrapAddr;

    @Value("${bootstrap.port}")
    int bootstrapPort;

    public BootstrapInitiator(Bootstrap bootstrap, CommandListener commandListener) {
        this.bootstrap = bootstrap;
        this.commandListener = commandListener;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        try {
            commandListener.start();

            InputParameters.refresh( bootstrapAddr, bootstrapPort, ip, port, username );
            bootstrap.connectToBootstrapServer(ip, port, bootstrapAddr, bootstrapPort, username);
        } catch (IOException e) {
            LOGGER.error("Registering to bootstrap failed.", e);
            System.exit(0);
        }
    }
}
