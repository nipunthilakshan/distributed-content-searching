package com.example.formicarymrt.client.models;

import lombok.Data;

import java.util.List;

public @Data
class SearchHit {

    private String ownerIp;
    private int ownerPort;
    private List<String> fileNames;
    private int hops;
}
