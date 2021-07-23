package com.dc.distributed.content.searching.processors;

import com.dc.distributed.content.searching.ApplicationState;
import com.dc.distributed.content.searching.CommandSender;
import com.dc.distributed.content.searching.commands.AbstractCommand;
import com.dc.distributed.content.searching.commands.SearchReply;
import com.dc.distributed.content.searching.models.SearchHit;
import com.dc.distributed.content.searching.performance.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchOkProcessor extends AbstractCommandProcessor<AbstractCommand> {

    @Autowired
    Map<String, RequestInfo> responseWaitMap;

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchOkProcessor.class);

    public SearchOkProcessor(ApplicationState applicationState, CommandSender commandSender) {

        super(applicationState, commandSender);
    }

    @Override
    protected void processSynchronously(AbstractCommand message) {

        SearchReply reply = (SearchReply) message;

        if (reply.getSearchHit() != null) {
            SearchHit searchHit = ((SearchReply) message).getSearchHit();
            responseWaitMap.forEach((k,requestInfo)->{
                List<SearchHit> searchHitList = requestInfo.getSearchHitList();
                System.out.println("======= END : "+System.currentTimeMillis());
                searchHit.setProcessTime(System.currentTimeMillis() - requestInfo.getStartTime());
                searchHitList.add(searchHit);
            });

//            searchHit.getFileNames().forEach(hit->{
//                RequestInfo requestInfo = responseWaitMap.get(hit);
//                if(requestInfo != null){
//                    searchHit.setProcessTime(System.currentTimeMillis()-requestInfo.getStartTime());
//                    List<SearchHit> searchHitList = requestInfo.getSearchHitList() !=null && !requestInfo.getSearchHitList().isEmpty() ? requestInfo.getSearchHitList() : new ArrayList<>();
//                    searchHitList.add(searchHit);
//                    requestInfo.setSearchHitList(searchHitList);
//                    requestInfo.setFound(true);
//                }
//            });
            LOGGER.info("Found search result: {}", searchHit);
            boolean hasMatchAlready = getApplicationState().getSearchHits().stream().anyMatch(hit ->
                    hit.getFileNames().equals(searchHit.getFileNames())
                            && hit.getOwnerIp().equals(searchHit.getOwnerIp())
                            && hit.getOwnerPort() == searchHit.getOwnerPort());
            if (!hasMatchAlready) {
                getApplicationState().getSearchHits().add(reply.getSearchHit());
            }
        } else {
            LOGGER.info("Received search failed from {}:{}", message.getOtherPartyIp(), message.getOtherPartyPort());
        }
    }
}
