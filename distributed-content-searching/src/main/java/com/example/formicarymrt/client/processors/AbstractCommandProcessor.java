package com.example.formicarymrt.client.processors;

import com.example.formicarymrt.client.ApplicationState;
import com.example.formicarymrt.client.CommandSender;
import lombok.Getter;

import java.io.IOException;

@Getter
public abstract class AbstractCommandProcessor<T> {

    private static final Object LOCK = new Object();

    private ApplicationState applicationState;
    private CommandSender commandSender;

    public AbstractCommandProcessor(ApplicationState applicationState, CommandSender commandSender) {
        this.applicationState = applicationState;
        this.commandSender = commandSender;
    }

    public final void process(T message) throws IOException
    {
        synchronized (LOCK) {
            processSynchronously(message);
        }
    }

    protected abstract void processSynchronously(T message) throws IOException;
}
