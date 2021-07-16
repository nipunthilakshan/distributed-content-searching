package com.example.formicarymrt.client.processors;

import com.example.formicarymrt.client.ApplicationState;
import com.example.formicarymrt.client.CommandSender;
import com.example.formicarymrt.client.commands.RegisterReply;
import com.example.formicarymrt.client.util.JoinRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RegOkProcessor extends AbstractCommandProcessor<RegisterReply> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegOkProcessor.class);


    public RegOkProcessor(ApplicationState applicationState, CommandSender commandSender) {
        super(applicationState, commandSender);
    }

    @Override
    protected void processSynchronously(RegisterReply reply) {
        ApplicationState appState = getApplicationState();
        //initiate potential neighbours list
        appState.getPotentialNeighbours().addAll(reply.getNeighbours());
        LOGGER.info("Potential Neighbours {}", Arrays.toString(appState.getPotentialNeighbours().toArray()));

        //get needed amount of neighbours
        LOGGER.info("REG OK received sendJoinCommandsToNeighbours");
        JoinRequestUtil.sendJoinCommandsToNeighbours(appState, getCommandSender());
    }
}
