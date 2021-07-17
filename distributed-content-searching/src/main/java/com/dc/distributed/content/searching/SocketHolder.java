package com.dc.distributed.content.searching;

import org.springframework.stereotype.Service;

import java.net.DatagramSocket;
import java.net.SocketException;

@Service
public class SocketHolder {

    private DatagramSocket socket;

    public synchronized void initSocket(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    public DatagramSocket getSocket() {
        return socket;
    }
}
