package com.dc.distributed.content.searching.parsers.requestparsers;

import com.dc.distributed.content.searching.commands.SearchReply;

import java.util.List;

public class SerOkRequestParser implements RequestParser<SearchReply> {

    private static final String SPACE = " ";

    @Override
    public String parse(SearchReply command) {
        // length SEROK no_files IP port hops filename1 filename2

        String serOkCommand = command.getCommandType();

        if (command.getSearchHit() == null) {
            return String.format("0000 %s 0", serOkCommand);
        }

        List<String> fileNames = command.getSearchHit().getFileNames();
        String staticLengthCommand = String.format("0000 %s %s %s %s %s", serOkCommand, fileNames.size(),
                command.getIssuerIp(), command.getIssuerPort(), command.getSearchHit().getHops());
        StringBuilder sb = new StringBuilder(staticLengthCommand);

        fileNames.forEach(f -> sb.append(SPACE).append(f.replace(" ", "\\ ")));
        return sb.toString();
    }
}
