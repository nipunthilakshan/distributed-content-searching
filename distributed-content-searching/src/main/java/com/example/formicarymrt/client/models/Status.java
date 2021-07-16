package com.example.formicarymrt.client.models;

import com.example.formicarymrt.client.Neighbour;
import com.example.formicarymrt.client.commands.SearchRequest;
import lombok.Data;

import java.util.List;

public @Data
class Status {
    private List<Neighbour> neighbours;
    private SearchForm searchForm;
    private List<SearchRequest> ongoingRequests;
    private LeaveForm leaveForm;
    private List<SearchHit> searchHits;
}
