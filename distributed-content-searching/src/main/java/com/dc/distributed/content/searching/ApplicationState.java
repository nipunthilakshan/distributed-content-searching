package com.dc.distributed.content.searching;

import com.dc.distributed.content.searching.commands.SearchRequest;
import com.dc.distributed.content.searching.models.SearchHit;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ApplicationState {

    @Getter
    private List<Neighbour> neighbours;

    @Value("${ongoingrequests.timetolive}")
    private int timeToLive;

    @Getter
    private List<Neighbour> potentialNeighbours;

    @Getter
    private Cache<String, SearchRequest> ongoingRequests;

    @Getter
    private List<SearchHit> searchHits;

    @Getter
    @Value("${client.ip}")
    private String ipAddress;

    @Getter
    @Value("${client.port}")
    private int port;

    @Getter
    @Value("${server.port}")
    private int downloadPort;

    public ApplicationState() {
        this.neighbours = new ArrayList<>();
        this.searchHits = new ArrayList<>();
        this.potentialNeighbours = new ArrayList<>();
        this.ongoingRequests = CacheBuilder.newBuilder()
                .expireAfterWrite(timeToLive, TimeUnit.SECONDS)
                .build();
    }

    public void addNeighbour(Neighbour neighbour) {
        this.neighbours.add(neighbour);
    }
}
