package com.dc.distributed.content.searching.performance;

import com.dc.distributed.content.searching.ApplicationState;
import com.dc.distributed.content.searching.CommandProcessor;
import com.dc.distributed.content.searching.models.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class PerformanceController {

    @Autowired
    ApplicationState applicationState;
    @Autowired
    CommandProcessor commandProcessor;

    @Autowired
    Map<String, RequestInfo> responseWaitMap;


    @GetMapping("/execute")
    public ResponseEntity execute(String fileName) throws InterruptedException {
        RequestInfo requestInfo = new RequestInfo();
        System.out.println("======= START : "+System.currentTimeMillis());
        requestInfo.setStartTime(System.currentTimeMillis());
        List<SearchHit> searchHitList = new ArrayList<>();
        requestInfo.setSearchHitList(searchHitList);
        responseWaitMap.put(fileName,requestInfo);
        commandProcessor.search(fileName);

        Thread.sleep(1000);
        responseWaitMap.clear();
        System.out.println("======== : "+ responseWaitMap.size());
        return ResponseEntity.status(HttpStatus.OK).body(requestInfo);
//        while (true){
//            for (Map.Entry<String, RequestInfo> entry : responseWaitMap.entrySet()) {
//                String k = entry.getKey();
//                RequestInfo r = entry.getValue();
//                if (k.equals(fileName)) {
//                    if (r.isFound()) {
//                        return ResponseEntity.status(HttpStatus.OK).body(r);
//                    }
//                }
//            }
//        }

    }
}
