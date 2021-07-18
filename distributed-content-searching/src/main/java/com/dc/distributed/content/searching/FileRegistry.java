package com.dc.distributed.content.searching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

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

    private final List<String> storedFiles;

    public FileRegistry() {

        List<String> allFiles = new ArrayList<>();

        //Read File Content
        try {
            File file = ResourceUtils.getFile("classpath:File-Names.txt");
            //File is found
            LOGGER.debug("File Found : {}", file.exists());
            allFiles = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Random random = new Random();
        storedFiles = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            if (allFiles.isEmpty()) {
                break;
            }
            storedFiles.add(allFiles.remove(random.nextInt(allFiles.size())));
        }

        LOGGER.info("Files selected for storing {}", storedFiles);
    }

    public boolean hasFile(String fileName) {

        return storedFiles.contains(fileName);
    }

    public List<String> getMatchingFiles(String fileName) {

        String pattern = String.format(".*\\b%s\\b.*", fileName);

        return storedFiles.stream().filter(e -> e.matches(pattern)).collect(Collectors.toList());
    }
}
