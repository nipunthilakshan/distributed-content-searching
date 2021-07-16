package com.example.formicarymrt.client.processors;

import com.example.formicarymrt.client.ApplicationState;
import com.example.formicarymrt.client.CommandSender;
import com.example.formicarymrt.client.Neighbour;
import com.example.formicarymrt.client.commands.JoinReply;
import com.example.formicarymrt.client.commands.JoinRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JoinRequestProcessor extends AbstractCommandProcessor<JoinRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JoinRequestProcessor.class);


    public JoinRequestProcessor(ApplicationState applicationState, CommandSender commandSender) {
        super(applicationState, commandSender);
    }

    @Override
    protected void processSynchronously(JoinRequest message) {
        //Join request received from another node
        Neighbour newNeighbour = new Neighbour(message.getOtherPartyIp(), message.getOtherPartyPort());
        LOGGER.info("Neighbour Join request received from " + newNeighbour);
        //check of the neighbour is already joined
        if (getApplicationState().getNeighbours().stream().noneMatch(neighbour -> neighbour.equals(newNeighbour))) {

            //Add the new neighbour
            LOGGER.info("Adding new Neighbour to the list " + newNeighbour);
            getApplicationState().addNeighbour(newNeighbour);

            //send JoinOk 0 (success) Message
            LOGGER.info("Sending join success to " + newNeighbour);
            JoinReply joinReply = new JoinReply(getApplicationState().getIpAddress(), getApplicationState().getPort(), message.getOtherPartyIp(), message.getOtherPartyPort(), true);
            try {
                getCommandSender().sendRequest(joinReply);
            } catch (IOException e) {
                LOGGER.error("Error in sending Join Ok success message", e);
            }
        } else {
            //send JoinOK 9999 (error) message
            LOGGER.info("Sending join failed to " + newNeighbour);
            JoinReply joinReply = new JoinReply(getApplicationState().getIpAddress(), getApplicationState().getPort(), message.getOtherPartyIp(), message.getOtherPartyPort(), false);
            try {
                getCommandSender().sendRequest(joinReply);
            } catch (IOException e) {
                LOGGER.error("Error in sending Join Ok success message", e);
            }
        }

    }


}

