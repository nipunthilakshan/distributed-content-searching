package com.dc.distributed.content.searching;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class FileRegistry {


    private static final Logger LOGGER = LoggerFactory.getLogger(FileRegistry.class);
    public static String NODE_ID = UUID.randomUUID().toString();

    public FileRegistry() {

        List<String> allFiles = new ArrayList<>();
        //Read File Content
        try {
            ClassPathResource resource = new ClassPathResource("File-Names.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            reader.lines().forEach(file -> allFiles.add(file.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int rFile = random.nextInt(allFiles.size());
            int rSize = (random.nextInt(5) + 1) * 2;
            String file = allFiles.remove(rFile);
            int file_size = rSize * 1024 * 1024;
            byte[] content = new byte[file_size];
            try {
                FileUtils.writeByteArrayToFile(new File(NODE_ID + "/" + file), content);
            } catch (Exception ex) {
                LOGGER.error("File Creation Error : {}", ex.getMessage());
            }
        }
    }

    public boolean hasFile(String fileName) {

        return getExistingFileList().keySet().contains(fileName);
    }

    public static Map<String, Integer> getStoredFiles() {

        return getExistingFileList();
    }

    public List<String> getMatchingFiles(String fileName) {

        String pattern = String.format(".*\\b%s\\b.*", fileName);

        return getExistingFileList().keySet().stream().filter(e -> e.matches(pattern)).collect(Collectors.toList());
    }


    private static Map<String, Integer> getExistingFileList() {
        File nodeFolder = new File(NODE_ID + "/");
        File[] files = nodeFolder.listFiles();
        Map<String, Integer> nodeFileList = new HashMap<>();
        for (int f = 0; f < files.length; f++) {
            nodeFileList.put(files[f].getName(), (int) (files[f].length() / (1024 * 1024)));
        }
        return nodeFileList;
    }
}
