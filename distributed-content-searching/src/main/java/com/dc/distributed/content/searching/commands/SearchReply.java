package com.dc.distributed.content.searching.commands;

import com.dc.distributed.content.searching.models.SearchHit;
import lombok.Getter;

@Getter
public class SearchReply extends AbstractCommand {

    public static final String SEROK = "SEROK";
    private SearchHit searchHit;

    public SearchReply(String issuerIp, int issuerPort, String requesterIp, int requesterPort, SearchHit searchHit) {
        super(issuerIp, issuerPort, requesterIp, requesterPort);
        this.searchHit = searchHit;
    }

    @Override
    public String getCommandType() {
        return SEROK;
    }
}
