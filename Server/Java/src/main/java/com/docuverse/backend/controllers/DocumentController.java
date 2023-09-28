package com.docuverse.backend.controllers;

import com.docuverse.backend.services.DocumentService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        for (MultipartFile pdfFile : pdfFiles) {
            executorService.submit(() -> {
                try {
                    System.out.println("Thread: " + executorService.toString());
                    String text = documentService.extractTextFromPDF(pdfFile);
                    Metadata metadata = documentService.createMetadataFromMultipartFile(pdfFile);
                    Document document = new Document(text,metadata);
                    System.out.println("Checkpoint document");
                    // Process or store the extracted text as needed.
                    documentService.documentToEmbedding(document,embeddingStore);

                } catch (IOException e) {
                    // Handle exceptions appropriately.
                }
            });
        }

        executorService.shutdown();

        return ResponseEntity.ok("Upload successful");
    }
}
