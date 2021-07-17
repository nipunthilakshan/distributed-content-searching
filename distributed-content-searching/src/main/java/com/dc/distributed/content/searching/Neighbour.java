package com.dc.distributed.content.searching;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Neighbour {
    private String ip;
    private int port;

    public Neighbour(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return this.ip;
    }

    public int getPort() {
        return this.port;
    }

    @Override
    public String toString() {
        return "Neighbour{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
