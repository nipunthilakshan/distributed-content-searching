package com.example.demo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(DemoApplication.class, args);

            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setReadTimeout(10000);
            factory.setConnectTimeout(10000);
            RestTemplate restTemplate = new RestTemplate(factory);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<?> entity = new HttpEntity("", headers);
            //Read File Content
            List<String> allFiles = new ArrayList<>();
            //FileWriter fileWriter = new FileWriter("src/main/resources/test.txt", true);
            try {
                ClassPathResource resource = new ClassPathResource("File-Names.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
                reader.lines().forEach(file -> allFiles.add(file.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Map<Integer,Integer> countMap = new HashMap<>();
            DemoApplication demoApplication = new DemoApplication();
            //demoApplication.getHopCountStats(allFiles,restTemplate,entity);
            List<String> nodeList = new ArrayList<>();
            nodeList.add("8008");
            nodeList.add("8005");
            nodeList.add("8007");
            Map<Integer, Integer> countMap = new HashMap<>();
            Map<Integer, Integer> timeMap = new HashMap<>();
            nodeList.forEach(node->{
                demoApplication.getHopCountStats(allFiles,restTemplate,entity,node,countMap);
                demoApplication.getTimeStats(allFiles,restTemplate,entity,node,timeMap);
            });
            countMap.forEach((k, v) -> {
                System.out.println("K : " + k + " V : " + v);
            });
            System.out.println("===========================================================================================");
            timeMap.forEach((k, v) -> {
                System.out.println("K : " + k + " V : " + v);
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void getHopCountStats(List<String> allFiles,RestTemplate restTemplate,HttpEntity<?> entity,String requestNode,Map<Integer, Integer> countMap ){
        try {

            for (String file : allFiles) {
                //fileWriter.write("\n");
                ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:"+requestNode+"/execute?fileName=" + file, HttpMethod.GET, entity, String.class);
                JSONObject jsonObject = new JSONObject(responseEntity.getBody());
                JSONArray searchHits = jsonObject.getJSONArray("searchHitList");
                Map<Integer, Integer> hopByNode = new HashMap<>();
                //fileWriter.write(file + " : Message Count : " + searchHits.length());
                for (int i = 0; i < searchHits.length(); i++) {
                    JSONObject hit = searchHits.getJSONObject(i);
                    int hopCount = hit.getInt("hops");
                    int node = hit.getInt("ownerPort");
                    if (hopByNode.get(node) == null) {
                        hopByNode.put(node, hopCount);
                    } else {
                        int existingNodeHopsCount = hopByNode.get(node);
                        if (existingNodeHopsCount > hopCount) {
                            hopByNode.put(node, hopCount);
                        }
                    }

                }
                hopByNode.forEach((node, hopCount) -> {
                    if (countMap.get(hopCount) == null) {
                        countMap.put(hopCount, 1);
                    } else {
                        countMap.put(hopCount, countMap.get(hopCount) + 1);
                    }
                });


            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void getTimeStats(List<String> allFiles,RestTemplate restTemplate,HttpEntity<?> entity,String requestNode, Map<Integer, Integer> timeMap){
        try {

            for (String file : allFiles) {
                //fileWriter.write("\n");
                ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:"+requestNode+"/execute?fileName=" + file, HttpMethod.GET, entity, String.class);
                JSONObject jsonObject = new JSONObject(responseEntity.getBody());
                JSONArray searchHits = jsonObject.getJSONArray("searchHitList");
                Map<Integer, TimeInfo> timeByNode = new HashMap<>();
                //fileWriter.write(file + " : Message Count : " + searchHits.length());
                for (int i = 0; i < searchHits.length(); i++) {
                    JSONObject hit = searchHits.getJSONObject(i);
                    int hopCount = hit.getInt("hops");
                    int node = hit.getInt("ownerPort");
                    int processTime = hit.getInt("processTime");
                    if (timeByNode.get(node) == null) {
                        TimeInfo timeInfo = new TimeInfo();
                        timeInfo.setHopCount(hopCount);
                        timeInfo.setProcessTime(processTime);
                        timeByNode.put(node, timeInfo);
                    } else {
                        TimeInfo timeInfo = timeByNode.get(node);
                        int existingNodeHopsCount = timeInfo.getHopCount();
                        if (existingNodeHopsCount > hopCount) {
                            timeInfo.setProcessTime(processTime);
                            timeInfo.setHopCount(hopCount);
                        }
                    }

                }
                timeByNode.forEach((node, timeInfo) -> {
                    if (timeMap.get(timeInfo.getProcessTime()) == null) {
                        timeMap.put(timeInfo.getProcessTime(), 1);
                    } else {
                        timeMap.put(timeInfo.getProcessTime(), timeMap.get(timeInfo.getProcessTime())+1);
                    }
                });


            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
