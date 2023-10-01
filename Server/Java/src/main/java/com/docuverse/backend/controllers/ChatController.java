package com.docuverse.backend.controllers;

import com.docuverse.backend.dtos.ChatResponseDTO;
import com.docuverse.backend.dtos.ChatRequestDTO;
import com.docuverse.backend.enums.MessageRole;
import com.docuverse.backend.services.ChatService;
import com.docuverse.backend.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/chatbot")
public class ChatController {

    private final ChatService chatService;
    private final MessageService messageService;

    @Autowired
    public ChatController(ChatService chatService, MessageService messageService) {
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponseDTO> chat(@RequestBody ChatRequestDTO request) {
        try {
            // Process the chat request using the ChatService
            messageService.createMessage(request.threadId(), request.question(), MessageRole.user);
            String response = chatService.processChat(request);

            // Optionally, you can log the request and response here
            ChatResponseDTO chatResponse = new ChatResponseDTO(response, MessageRole.assistant);
            messageService.createMessage(request.threadId(), chatResponse.response(), MessageRole.assistant);

            return ResponseEntity.ok(chatResponse);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate error response
            ChatResponseDTO chatResponse = new ChatResponseDTO("Something went wrong", MessageRole.error);
            return ResponseEntity.internalServerError().body(chatResponse);
        }
    }
}

