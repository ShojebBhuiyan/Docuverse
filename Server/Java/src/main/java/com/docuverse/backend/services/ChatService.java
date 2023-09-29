package com.docuverse.backend.services;

import com.docuverse.backend.models.ChatRequest;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;

public interface ChatService {
    /**
     * Process a chat request and return a response.
     *
     * @param request The chat request containing user input.
     * @return The response generated based on the chat request.
     */
    String processChat(ChatRequest request, EmbeddingStore<TextSegment> embeddingStore);
}

