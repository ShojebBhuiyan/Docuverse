package com.docuverse.backend.controllers;

import com.docuverse.backend.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/v1/document")
@CrossOrigin(origins = "http://localhost:3000")
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPDFs(@RequestParam("files") MultipartFile[] pdfFiles) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        System.out.println("STARTED");
        for (MultipartFile pdfFile : pdfFiles) {
            executorService.submit(() -> {
                try {
                    System.out.println("Thread: " + executorService.toString());
                    String text = documentService.extractTextFromPDF(pdfFile);
                    // Process or store the extracted text as needed.
                } catch (IOException e) {
                    // Handle exceptions appropriately.
                }
            });
        }

        executorService.shutdown();

        return ResponseEntity.ok("Upload successful");
    }
}
