package com.dc.distributed.content.searching.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputParameters {

    private static String bootstrapAddr;
    private static int bootstrapPort;
    private static String ip;
    private static int port;
    private static String username;

    public static void refresh(String bootstrapAddr, int bootstrapPort, String ip, int port, String username) {

        InputParameters.setBootstrapAddr(bootstrapAddr);
        InputParameters.setBootstrapPort(bootstrapPort);
        InputParameters.setIp(ip);
        InputParameters.setPort(port);
        InputParameters.setUsername(username);
    }

    public static String getBootstrapAddr() {

        return bootstrapAddr;
    }

    private static void setBootstrapAddr(String bootstrapAddr) {

        InputParameters.bootstrapAddr = bootstrapAddr;
    }

    public static int getBootstrapPort() {

        return bootstrapPort;
    }

    private static void setBootstrapPort(int bootstrapPort) {

        InputParameters.bootstrapPort = bootstrapPort;
    }

    public static String getIp() {

        return ip;
    }

    private static void setIp(String ip) {

        InputParameters.ip = ip;
    }

    public static int getPort() {

        return port;
    }

    private static void setPort(int port) {

        InputParameters.port = port;
    }

    public static String getUsername() {

        return username;
    }

    private static void setUsername(String username) {

        InputParameters.username = username;
    }
}
