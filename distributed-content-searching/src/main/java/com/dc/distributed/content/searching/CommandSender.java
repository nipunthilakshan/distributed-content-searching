package com.dc.distributed.content.searching;

import com.dc.distributed.content.searching.commands.AbstractCommand;
import com.dc.distributed.content.searching.parsers.CommandParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Service
public class CommandSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandSender.class);

    private final CommandParser commandParser;
    private final SocketHolder socketHolder;

    public CommandSender(CommandParser commandParser, SocketHolder socketHolder) {
        this.commandParser = commandParser;
        this.socketHolder = socketHolder;
    }

    public void sendRequest(AbstractCommand request) throws IOException {
        String requestStr = commandParser.parse(request);
        DatagramSocket socket = socketHolder.getSocket();
        InetAddress address = InetAddress.getByName(request.getOtherPartyIp());

        LOGGER.info("Sending request to {}:{}, \"{}\".", request.getOtherPartyIp(), request.getOtherPartyPort(), requestStr);

        socket.send(new DatagramPacket(requestStr.getBytes(), requestStr.getBytes().length, address, request.getOtherPartyPort()));
    }
}
