package com.dc.distributed.content.searching.processors;

import com.dc.distributed.content.searching.ApplicationState;
import com.dc.distributed.content.searching.CommandSender;
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
