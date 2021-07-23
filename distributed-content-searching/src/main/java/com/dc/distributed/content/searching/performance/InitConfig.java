package com.dc.distributed.content.searching.performance;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class InitConfig {

    @Bean
    public Map<String, RequestInfo> responseWaitMap() {
        return new ConcurrentHashMap<String, RequestInfo>();
    }


}
