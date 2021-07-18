package com.dc.distributed.content.searching.processors;

import com.dc.distributed.content.searching.Bootstrap;
import com.dc.distributed.content.searching.models.InputParameters;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UnregisterProcessor {

    private final Bootstrap bootstrap;

    public UnregisterProcessor(Bootstrap bootstrap) {

        this.bootstrap = bootstrap;
    }

    public void unregister() throws IOException {

        bootstrap.disconnectFromBootstrapServer(InputParameters.getBootstrapAddr(), InputParameters.getBootstrapPort(), InputParameters.getIp(), InputParameters.getPort(), InputParameters.getUsername());
    }
}
