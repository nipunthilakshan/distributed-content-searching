package com.dc.distributed.content.searching;

import com.dc.distributed.content.searching.util.ResourceLoader;
import java.util.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DistributedContentSearchingApplication {

    public static long WAIT_TIME_MS = 1000;
    public static Properties externalAppProperties;

    public static void main(String[] args) {

        externalAppProperties = ResourceLoader.loadAppProperties("external.app.properties");
        Scanner in = new Scanner(System.in);

        String bootstrapIp = externalAppProperties.getProperty("bootstrap.ip");
        String bootstrapPort = externalAppProperties.getProperty("bootstrap.port");
        String clientIp = externalAppProperties.getProperty("client.ip");

        System.out.print("Client Port: ");
        String clientPort = in.nextLine();

        System.out.print("HTTP Port(For User Interface): ");
        String httpPort = in.nextLine();

        System.out.print("Username: ");
        String username = in.nextLine();

        WAIT_TIME_MS = Long.parseLong(externalAppProperties.getProperty("wait.time"));


        int originalArgsLen = args.length;
        String[] extendedArgs = new String[originalArgsLen + 6];
        System.arraycopy(args, 0, extendedArgs, 0, originalArgsLen);

        extendedArgs[originalArgsLen] = String.format("--bootstrap.ip=%s", bootstrapIp);
        extendedArgs[originalArgsLen + 1] = String.format("--client.ip=%s", clientIp);
        extendedArgs[originalArgsLen + 2] = String.format("--client.port=%s", clientPort);
        extendedArgs[originalArgsLen + 3] = String.format("--server.port=%s", httpPort);
        extendedArgs[originalArgsLen + 4] = String.format("--client.username=%s", username);
        extendedArgs[originalArgsLen + 5] = String.format("--bootstrap.port=%s", bootstrapPort);

        SpringApplication.run(DistributedContentSearchingApplication.class, extendedArgs);
    }

}
