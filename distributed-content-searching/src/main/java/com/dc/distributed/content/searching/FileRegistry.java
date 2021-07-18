package com.dc.distributed.content.searching;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileRegistry {


    private static final Logger LOGGER = LoggerFactory.getLogger(FileRegistry.class);
    public static String NODE_ID = UUID.randomUUID().toString();

    private final Map<String,Integer> storedFiles;

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
        storedFiles = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            int rFile = random.nextInt(allFiles.size());
            int rSize = random.nextInt(5);
            storedFiles.put(allFiles.remove(rFile),rSize+1);
        }
        storedFiles.forEach((file,size) ->{
            try {
                int file_size = size*2*1024*1024;
                byte[] content = new byte[file_size];
                FileUtils.writeByteArrayToFile(new File(NODE_ID+"/"+file), content);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
        LOGGER.info("Files selected for storing {}", storedFiles);
    }

    public boolean hasFile(String fileName) {

        return storedFiles.keySet().contains(fileName);
    }

    public List<String> getMatchingFiles(String fileName) {

        String pattern = String.format(".*\\b%s\\b.*", fileName);

        return storedFiles.keySet().stream().filter(e -> e.matches(pattern)).collect(Collectors.toList());
    }
}
