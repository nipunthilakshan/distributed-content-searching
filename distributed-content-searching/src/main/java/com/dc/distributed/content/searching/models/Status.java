package com.dc.distributed.content.searching.models;

import com.dc.distributed.content.searching.Neighbour;
import com.dc.distributed.content.searching.commands.SearchRequest;
import java.util.Map;
import lombok.Data;

import java.util.List;

public @Data
class Status {

    private List<Neighbour> neighbours;
    private SearchForm searchForm;
    private List<SearchRequest> ongoingRequests;
    private LeaveForm leaveForm;
    private List<SearchHit> searchHits;
    private Map<String, Integer> storedFiles;
}
