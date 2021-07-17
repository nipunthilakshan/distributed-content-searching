package com.dc.distributed.content.searching.processors;

import com.dc.distributed.content.searching.ApplicationState;
import com.dc.distributed.content.searching.CommandSender;
import com.dc.distributed.content.searching.Neighbour;
import com.dc.distributed.content.searching.commands.JoinReply;
import com.dc.distributed.content.searching.util.JoinRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JoinOkProcessor extends AbstractCommandProcessor<JoinReply> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JoinOkProcessor.class);

    public JoinOkProcessor(ApplicationState applicationState, CommandSender commandSender) {

        super(applicationState, commandSender);
    }

    @Override
    protected void processSynchronously(JoinReply message) {

        if (message.isJoinSuccess()) {
            //if joined add to neighbours list
            LOGGER.info("Successfully Joined {} {}", message.getOtherPartyIp(), message.getOtherPartyPort());
            Neighbour newNeighbour = new Neighbour(message.getOtherPartyIp(), message.getOtherPartyPort());
            getApplicationState().getNeighbours().add(newNeighbour);
            LOGGER.info("New neighbour added " + newNeighbour);

        } else {
            //if join failed try joining another from potential neighbours list
            LOGGER.info("Error while adding new node to routing table {}:{}", message.getOtherPartyIp(), message.getOtherPartyPort());
            LOGGER.info("Trying to connect to other potential neighbours");
            JoinRequestUtil.sendJoinCommandsToNeighbours(getApplicationState(), getCommandSender());
        }
    }
}
