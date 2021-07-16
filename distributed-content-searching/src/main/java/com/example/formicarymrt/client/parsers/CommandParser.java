package com.example.formicarymrt.client.parsers;

import com.example.formicarymrt.client.commands.AbstractCommand;
import com.example.formicarymrt.client.commands.JoinReply;
import com.example.formicarymrt.client.commands.JoinRequest;
import com.example.formicarymrt.client.commands.LeaveReply;
import com.example.formicarymrt.client.commands.LeaveRequest;
import com.example.formicarymrt.client.commands.RegisterReply;
import com.example.formicarymrt.client.commands.RegisterRequest;
import com.example.formicarymrt.client.commands.SearchReply;
import com.example.formicarymrt.client.commands.SearchRequest;
import com.example.formicarymrt.client.commands.UnregisterReply;
import com.example.formicarymrt.client.commands.UnregisterRequest;
import com.example.formicarymrt.client.parsers.replyparsers.*;
import com.example.formicarymrt.client.parsers.requestparsers.*;
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
