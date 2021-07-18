package com.dc.distributed.content.searching.initiators;

import com.dc.distributed.content.searching.SocketHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.SocketException;

@Order(0)
@Component
public class SocketInitiator implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketInitiator.class);

    @Value("${client.port}")
    int clientPort;

    private final SocketHolder socketHolder;

    public SocketInitiator(SocketHolder socketHolder) {

        this.socketHolder = socketHolder;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        try {
            socketHolder.initSocket(clientPort);
        } catch (SocketException e) {
            LOGGER.error("Socket initialisation failed.", e);
            System.exit(0);
        }
    }
}
