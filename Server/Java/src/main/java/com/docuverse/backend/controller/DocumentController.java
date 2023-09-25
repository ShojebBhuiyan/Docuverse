package com.docuverse.backend.controller;

import com.docuverse.backend.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/v1/document")
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPDFs(@RequestParam("pdfFiles") MultipartFile[] pdfFiles) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (MultipartFile pdfFile : pdfFiles) {
            executorService.submit(() -> {
                try {
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
