package com.example.formicarymrt.client.parsers.requestparsers;

import com.example.formicarymrt.client.commands.AbstractCommand;

public interface RequestParser<T extends AbstractCommand> {
    String parse(T command);

    default String setCommandLength(String command) {
        return command.replaceFirst("0000", String.format("%04d", command.length()));
    }
}
