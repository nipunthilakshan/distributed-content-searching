package com.dc.distributed.content.searching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class FileRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRegistry.class);

    private List<String> storedFiles;

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
