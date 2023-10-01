package com.docuverse.backend.services;

import com.docuverse.backend.dtos.ChatRequestDTO;

public interface ChatService {
    /**
     * Process a chat request and return a response.
     *
     * @param request The chat request containing user input.
     * @return The response generated based on the chat request.
     */
    String processChat(ChatRequestDTO request);
}

