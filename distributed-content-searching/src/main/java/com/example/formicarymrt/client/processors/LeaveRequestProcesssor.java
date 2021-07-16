package com.example.formicarymrt.client.processors;

import com.example.formicarymrt.client.ApplicationState;
import com.example.formicarymrt.client.CommandSender;
import com.example.formicarymrt.client.Neighbour;
import com.example.formicarymrt.client.commands.JoinReply;
import com.example.formicarymrt.client.commands.JoinRequest;
import com.example.formicarymrt.client.commands.LeaveReply;
import com.example.formicarymrt.client.commands.LeaveRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LeaveRequestProcesssor extends AbstractCommandProcessor<LeaveRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LeaveRequestProcesssor.class);

    public LeaveRequestProcesssor(ApplicationState applicationState, CommandSender commandSender) {
        super(applicationState, commandSender);
    }

    @Override
    protected void processSynchronously(LeaveRequest message) throws IOException {
        //Leave request received from another node

        Neighbour newNeighbour = new Neighbour(message.getOtherPartyIp(), message.getOtherPartyPort());
        LOGGER.info("Neighbour Leave request received from " + newNeighbour);

        if (getApplicationState().getNeighbours().stream().anyMatch(neighbour -> neighbour.equals(newNeighbour))) {
            getApplicationState().getNeighbours().removeIf(neighbour -> neighbour.equals(newNeighbour));

            LOGGER.info("Sending LEAVEOK success to " + newNeighbour);
            LeaveReply leaveReply = new LeaveReply(getApplicationState().getIpAddress(), getApplicationState().getPort(), message.getOtherPartyIp(), message.getOtherPartyPort(), true);
            try {
                getCommandSender().sendRequest(leaveReply);
            } catch (IOException e) {
                LOGGER.error("Error in sending LEAVEOK success message", e);
            }
        } else {
            //send LEAVEOK 9999 (error) message
            LOGGER.info("Sending leave failed to " + newNeighbour);
            LeaveReply leaveReply = new LeaveReply(getApplicationState().getIpAddress(), getApplicationState().getPort(), message.getOtherPartyIp(), message.getOtherPartyPort(), false);
            try {
                getCommandSender().sendRequest(leaveReply);
            } catch (IOException e) {
                LOGGER.error("Error in sending LEAVEOK success message", e);
            }
        }
    }
}
