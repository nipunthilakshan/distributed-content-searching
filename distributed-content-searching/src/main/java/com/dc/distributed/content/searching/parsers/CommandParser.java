package com.dc.distributed.content.searching.parsers;

import com.dc.distributed.content.searching.commands.AbstractCommand;
import com.dc.distributed.content.searching.commands.JoinReply;
import com.dc.distributed.content.searching.commands.JoinRequest;
import com.dc.distributed.content.searching.commands.LeaveReply;
import com.dc.distributed.content.searching.commands.LeaveRequest;
import com.dc.distributed.content.searching.commands.RegisterReply;
import com.dc.distributed.content.searching.commands.RegisterRequest;
import com.dc.distributed.content.searching.commands.SearchReply;
import com.dc.distributed.content.searching.commands.SearchRequest;
import com.dc.distributed.content.searching.commands.UnregisterReply;
import com.dc.distributed.content.searching.commands.UnregisterRequest;
import com.dc.distributed.content.searching.parsers.replyparsers.*;
import com.dc.distributed.content.searching.parsers.requestparsers.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class CommandParser {

    private Map<String, Supplier<RequestParser>> requestParsers;
    private Map<String, Supplier<ReplyParser>> replyParsers;

    public CommandParser() {

        initialiseRequestParsers();
        initialiseReplyParsers();
    }

    private static final String SPLIT_REGEX = "(?<!\\\\)\\s+";

    public AbstractCommand parse(String commandString, InetAddress requesterAddr, int requesterPort) throws IOException {

        String[] parts = commandString.split(SPLIT_REGEX);
        return replyParsers.getOrDefault(parts[1], DefaultParser::new).get().parse(parts, requesterAddr, requesterPort);
    }

    public String parse(AbstractCommand command) {

        RequestParser<AbstractCommand> parser = requestParsers.get(command.getCommandType()).get();
        String parsedCommand = parser.parse(command);
        return parser.setCommandLength(parsedCommand);
    }

    private void initialiseRequestParsers() {

        this.requestParsers = new HashMap<>();
        requestParsers.put(RegisterRequest.REG, RegRequesterParser::new);
        requestParsers.put(SearchRequest.SER, SearchRequestParser::new);
        requestParsers.put(SearchReply.SEROK, SerOkRequestParser::new);
        requestParsers.put(UnregisterRequest.UNREG, UnRegRequestParser::new);
        requestParsers.put(JoinRequest.JOIN, JoinRequestParser::new);
        requestParsers.put(JoinReply.JOIN_OK, JoinOkParser::new);
        requestParsers.put(LeaveRequest.LEAVE, LeaveRequestParser::new);
        requestParsers.put(LeaveReply.LEAVEOK, LeaveOKParser::new);
    }

    private void initialiseReplyParsers() {

        this.replyParsers = new HashMap<>();
        replyParsers.put(RegisterReply.REGOK, RegReplyParser::new);
        replyParsers.put(SearchReply.SEROK, SearchOkParser::new);
        replyParsers.put(SearchRequest.SER, SearchParser::new);
        replyParsers.put(UnregisterReply.UNREGOK, UnRegReplyParser::new);
        replyParsers.put(JoinReply.JOIN_OK, JoinReplyParser::new);
        replyParsers.put(JoinRequest.JOIN, JoinResponseParser::new);
        replyParsers.put(LeaveReply.LEAVEOK, LeaveReplyParser::new);
        replyParsers.put(LeaveRequest.LEAVE, LeaveResponseParser::new);
    }
}
