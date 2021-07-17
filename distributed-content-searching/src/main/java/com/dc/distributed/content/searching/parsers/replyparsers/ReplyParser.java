package com.dc.distributed.content.searching.parsers.replyparsers;

import com.dc.distributed.content.searching.commands.AbstractCommand;

import java.io.IOException;
import java.net.InetAddress;

public interface ReplyParser {

    AbstractCommand parse(String[] commandParts, InetAddress requesterAddr, int requesterPort) throws IOException;

    default int getInt(String str) {

        return Integer.parseInt(str);
    }
}
