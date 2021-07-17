package com.dc.distributed.content.searching.parsers;

import com.dc.distributed.content.searching.commands.AbstractCommand;
import com.dc.distributed.content.searching.parsers.replyparsers.ReplyParser;
import com.dc.distributed.content.searching.parsers.requestparsers.RequestParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

public class DefaultParser implements RequestParser<AbstractCommand>, ReplyParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultParser.class);

    @Override
    public AbstractCommand parse(String[] commandParts, InetAddress requesterAddr, int requesterPort) {
        LOGGER.info("{} is not a recognised command.", commandParts[1]);
        return null;
    }

    @Override
    public String parse(AbstractCommand command) {
        LOGGER.info("{} is not a recognised command.", command);
        return null;
    }
}
