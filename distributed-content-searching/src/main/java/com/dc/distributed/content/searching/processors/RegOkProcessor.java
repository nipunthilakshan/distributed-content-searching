package com.dc.distributed.content.searching.processors;

import com.dc.distributed.content.searching.ApplicationState;
import com.dc.distributed.content.searching.CommandSender;
import com.dc.distributed.content.searching.commands.RegisterReply;
import com.dc.distributed.content.searching.util.JoinRequestUtil;
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
