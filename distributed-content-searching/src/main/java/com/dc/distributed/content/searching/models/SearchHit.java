package com.dc.distributed.content.searching.models;

import lombok.Data;

import java.util.List;

public @Data
class SearchHit {

    private String ownerIp;
    private int ownerPort;
    private List<String> fileNames;
    private int hops;
}
