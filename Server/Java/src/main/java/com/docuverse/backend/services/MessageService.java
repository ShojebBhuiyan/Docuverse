package com.docuverse.backend.services;

import com.docuverse.backend.enums.MessageRole;
import com.docuverse.backend.exceptions.AppException;
import com.docuverse.backend.models.Message;
import com.docuverse.backend.models.Thread;
import com.docuverse.backend.models.User;
import com.docuverse.backend.repositories.MessageRepository;
import com.docuverse.backend.repositories.ThreadRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ThreadRepository threadRepository;

    public List<Message> getAllThreadMessages(Long threadId) {
        Thread thread  = threadRepository.findById(threadId)
                .orElseThrow(() -> new AppException("Unknown thread", HttpStatus.NOT_FOUND));
        return messageRepository.findByThread(thread);
    }

    public Message createMessage(Long threadId, String content, MessageRole role) {
        Thread thread  = threadRepository.findById(threadId)
                .orElseThrow(() -> new AppException("Unknown thread", HttpStatus.NOT_FOUND));
        Message message = new Message();
        message.setContent(content);
        message.setRole(role);
        message.setThread(thread);
        message.setDateTime(DateTime.now());

        return messageRepository.save(message);
    }
}
