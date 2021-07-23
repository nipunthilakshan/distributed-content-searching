package com.dc.distributed.content.searching.performance;

import com.dc.distributed.content.searching.models.SearchHit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestInfo {
    private long startTime;
    private boolean found;
    List<SearchHit> searchHitList;
}
