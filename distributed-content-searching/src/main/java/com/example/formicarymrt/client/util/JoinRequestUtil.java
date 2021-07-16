package com.example.formicarymrt.client.util;

import com.example.formicarymrt.client.ApplicationState;
import com.example.formicarymrt.client.CommandSender;
import com.example.formicarymrt.client.Neighbour;
import com.example.formicarymrt.client.commands.JoinRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;


public class JoinRequestUtil {

    private JoinRequestUtil() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(JoinRequestUtil.class);
    private static final int MINIMUM_NEIGHBOURS = 2;
    private static final Random random = new Random();


    public static boolean hasEnoughNeighbours(ApplicationState applicationState) {
        return applicationState.getNeighbours().size() >= MINIMUM_NEIGHBOURS;
    }


    public static int noOfNeighboursNeeded(ApplicationState applicationState) {
        //return 0 if negative number
        return Math.max(MINIMUM_NEIGHBOURS - applicationState.getNeighbours().size(), 0);
    }

    public static void sendJoinCommandsToNeighbours(ApplicationState appState, CommandSender commandSender) {
        //Check if Node has enough neighbours
        if (JoinRequestUtil.hasEnoughNeighbours(appState)) {
            LOGGER.info("Node has enough neighbours bypassing join command ");
            return;
        }

        int noOfNeighboursNeeded = noOfNeighboursNeeded(appState);
        LOGGER.info("No of Neighbours needed = {}", noOfNeighboursNeeded);

        while (noOfNeighboursNeeded > 0 && !appState.getPotentialNeighbours().isEmpty()) {
            //get random neighbour
            Neighbour randomNeighbour = appState.getPotentialNeighbours().remove(random.nextInt(appState.getPotentialNeighbours().size()));
            //create join request
            JoinRequest joinRequest = new JoinRequest(appState.getIpAddress(), appState.getPort(), randomNeighbour.getIp(), randomNeighbour.getPort());
            LOGGER.info(joinRequest.toString());
            try {
                commandSender.sendRequest(joinRequest);
            } catch (IOException e) {
                LOGGER.error("Error in sending the join command ", e);
            }
        }
    }
}
