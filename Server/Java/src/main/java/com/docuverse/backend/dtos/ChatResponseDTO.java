package com.docuverse.backend.dtos;

import com.docuverse.backend.enums.MessageRole;

public record ChatResponseDTO(String response, MessageRole role) {
}
