package com.dc.distributed.content.searching.parsers.replyparsers;

import com.dc.distributed.content.searching.Neighbour;
import com.dc.distributed.content.searching.commands.RegisterReply;
import com.dc.distributed.content.searching.processors.UnregisterProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

@Service
public class RegReplyParser implements ReplyParser
{
    private static final Logger LOGGER = LoggerFactory.getLogger( RegReplyParser.class );

    @Autowired
    private UnregisterProcessor unregisterProcessor;

    @Override
    public RegisterReply parse( String[] commandParts, InetAddress requesterAddr, int requesterPort ) throws IOException
    {
        ArrayList<Neighbour> neighbours = new ArrayList<>();

        int numberOfNeighbours = Integer.parseInt(commandParts[2]);

        if( numberOfNeighbours == 9998 )
        {
            LOGGER.info("Unregistering node");
            unregisterProcessor.unregister();
            return null;
        }

        if (numberOfNeighbours > 0) {
            // fist 3 elements are metadata
            for (int i = 3; i < commandParts.length; i = i + 2) {
                Neighbour neighbour = new Neighbour(commandParts[i], Integer.parseInt(commandParts[i + 1]));
                LOGGER.info("Found neighbour: {}.", neighbour);
                neighbours.add(neighbour);
            }
        }
        return new RegisterReply(null, -1, requesterAddr.getHostAddress(), requesterPort, neighbours);
    }
}
