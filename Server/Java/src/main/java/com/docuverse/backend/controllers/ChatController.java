package com.docuverse.backend.controllers;

import com.docuverse.backend.models.ChatRequest;
import com.docuverse.backend.services.ChatService;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatService chatService;


    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("api/v1/chat")
    public String chat(@RequestBody ChatRequest request) {
        try {
            // Process the chat request using the ChatService
            String response = chatService.processChat(request);

            // Optionally, you can log the request and response here

            return response;
        } catch (Exception e) {
            // Handle exceptions and return an appropriate error response
            return "An error occurred: " + e.getMessage();
        }
    }
}

