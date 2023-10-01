package com.docuverse.backend.controllers;

import com.docuverse.backend.services.DocumentService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import com.docuverse.backend.services.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/api/v1/document")
@CrossOrigin(origins = "http://localhost:3000")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Autowired
    StorageService storageService;
    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPDFs(@RequestParam("files") MultipartFile[] pdfFiles, @RequestParam("threadId") String threadId) {
        // ExecutorService executorService = Executors.newFixedThreadPool(2);
        System.out.println("STARTED");

        // EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        for (MultipartFile pdfFile : pdfFiles) {
            threadPoolExecutor.submit(() -> {
                try {
                    // Upload the PDF to S3 concurrently
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    System.out.println("Upload Thread");
                    System.out.println("Thread ID: " + threadId);
                    storageService.uploadFile(pdfFile, threadId);
                } catch (Exception e) {
                    System.out.println("Error uploading");
                    e.printStackTrace();
                }
            });
            threadPoolExecutor.submit(() -> {
                try {
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    System.out.println("Extract Thread");
                    String text = documentService.extractTextFromPDF(pdfFile);
                    Metadata metadata = documentService.createMetadataFromMultipartFile(pdfFile);
                    Document document = new Document(text, metadata);
                    System.out.println("Checkpoint document");
                    // Process or store the extracted text as needed.
                    documentService.documentToEmbedding(document);

                } catch (IOException e) {
                    // Handle exceptions appropriately.
                }
            });
        }

        // threadPoolExecutor.shutdown();

        return ResponseEntity.ok("Upload successful");
    }
}
