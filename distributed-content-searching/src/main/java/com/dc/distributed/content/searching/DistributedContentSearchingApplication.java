package com.dc.distributed.content.searching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DistributedContentSearchingApplication {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.print("Bootstrap IP Address: ");
        String bootstrapIp = in.nextLine();

        System.out.print("Client IP Address: ");
        String clientIp = in.nextLine();

        System.out.print("Client Port: ");
        String clientPort = in.nextLine();

        System.out.print("HTTP Port(For User Interface): ");
        String httpPort = in.nextLine();

        System.out.print("Username: ");
        String username = in.nextLine();

        int originalArgsLen = args.length;
        String[] extendedArgs = new String[originalArgsLen + 5];
        System.arraycopy(args, 0, extendedArgs, 0, originalArgsLen);

        extendedArgs[originalArgsLen] = String.format("--bootstrap.ip=%s", bootstrapIp);
        extendedArgs[originalArgsLen + 1] = String.format("--client.ip=%s", clientIp);
        extendedArgs[originalArgsLen + 2] = String.format("--client.port=%s", clientPort);
        extendedArgs[originalArgsLen + 3] = String.format("--server.port=%s", httpPort);
        extendedArgs[originalArgsLen + 4] = String.format("--client.username=%s", username);

        SpringApplication.run(DistributedContentSearchingApplication.class, extendedArgs);
    }

}
