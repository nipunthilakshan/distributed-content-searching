package com.dc.distributed.content.searching;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        allFiles.add("Adventures of Tintin");
        allFiles.add("Jack and Jill");
        allFiles.add("Glee");
        allFiles.add("The Vampire Diarie");
        allFiles.add("King Arthur");
        allFiles.add("Windows XP");
        allFiles.add("Harry Potter");
        allFiles.add("Kung Fu Panda");
        allFiles.add("Lady Gaga");
        allFiles.add("Twilight");
        allFiles.add("Windows 8");
        allFiles.add("Mission Impossible");
        allFiles.add("Turn Up The Music");
        allFiles.add("Super Mario");
        allFiles.add("American Pickers");
        allFiles.add("Microsoft Office 2010");
        allFiles.add("Happy Feet");
        allFiles.add("Modern Family");
        allFiles.add("American Idol");
        allFiles.add("Hacking for Dummies");

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

    public Map<String, Integer> getStoredFiles() {

        return getExistingFileList();
    }

    public List<String> getMatchingFiles(String fileName) {

        String pattern = String.format(".*\\b%s\\b.*", fileName);

        return getExistingFileList().keySet().stream().filter(e -> e.matches(pattern)).collect(Collectors.toList());
    }


    private Map<String, Integer> getExistingFileList() {
        File nodeFolder = new File(NODE_ID + "/");
        File[] files = nodeFolder.listFiles();
        Map<String, Integer> nodeFileList = new HashMap<>();
        for (int f = 0; f < files.length; f++) {
            nodeFileList.put(files[f].getName(), (int) (files[f].length() / (1024 * 1024)));
        }
        return nodeFileList;
    }
}
