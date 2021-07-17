package com.dc.distributed.content.searching.controllers;

import com.dc.distributed.content.searching.FileRegistry;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class DownloadController {

    private FileRegistry fileRegistry;
    private Random random;

    public DownloadController(FileRegistry fileRegistry) {
        this.fileRegistry = fileRegistry;
        this.random = new Random();
    }

    @GetMapping("/download/{file_name}")
    public ResponseEntity<Resource> download(@PathVariable("file_name") String fileName) {

        if (this.fileRegistry.hasFile(fileName)) {
            byte[] content = new byte[2048];
            random.nextBytes(content);

            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(new ByteArrayResource(content));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
