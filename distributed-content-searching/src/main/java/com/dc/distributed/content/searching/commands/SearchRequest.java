package com.dc.distributed.content.searching.commands;

import lombok.Getter;

@Getter
public class SearchRequest extends AbstractCommand {

    public static final String SER = "SER";

    private String fileName;
    private int hopCount;

    public SearchRequest(String issuerIp, int issuerPort, String destAddress, int destPort, String fileName, int hopCount) {
        super(issuerIp, issuerPort, destAddress, destPort);
        this.fileName = fileName;
        this.hopCount = hopCount;
    }

    @Override
    public String getCommandType() {
        return SER;
    }
}
