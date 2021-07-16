package com.example.formicarymrt.client;

import com.example.formicarymrt.client.commands.AbstractCommand;
import com.example.formicarymrt.client.parsers.CommandParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommandListener implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandListener.class);

    private final List<OnCommandListener> listeners;
    private final CommandParser commandParser;
    private final SocketHolder socketHolder;
    private Thread thread;

    public CommandListener(CommandParser commandParser, SocketHolder socketHolder) {
        listeners = new ArrayList<>();
        this.commandParser = commandParser;
        this.socketHolder = socketHolder;
    }

    public void start() {
        this.thread = new Thread(this);
        this.thread.start();
    }

    public void registerOnCommandListener(OnCommandListener onCommandListener) {
        this.listeners.add(onCommandListener);
    }

    @Override
    public void run() {
        DatagramSocket socket = socketHolder.getSocket();

        LOGGER.info("Listening for incoming requests. . .");

        while (true) {
            // continuously listen for messages

            byte[] buffer = new byte[65536];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                String data = new String(packet.getData(), 0, packet.getData().length);
                data = data.trim();

                LOGGER.info("Received command {}.", data);
                AbstractCommand command = commandParser.parse(data, packet.getAddress(), packet.getPort());

                if (command == null) {
                    continue;
                }

                listeners.forEach(listener -> {
                    try {
                        listener.onCommand(command);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                LOGGER.error("Receive failed.", e);
            }
        }
    }

    public interface OnCommandListener {
        void onCommand(AbstractCommand command) throws IOException;
    }
}
