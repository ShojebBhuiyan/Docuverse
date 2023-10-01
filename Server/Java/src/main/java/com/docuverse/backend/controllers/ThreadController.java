package com.docuverse.backend.controllers;

import com.docuverse.backend.configuration.UserAuthenticationProvider;
import com.docuverse.backend.models.Thread;
import com.docuverse.backend.services.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/thread")
public class ThreadController {
    private final ThreadService threadService;
    private final UserAuthenticationProvider userAuthenticationProvider;
    @PostMapping("/")
    public ResponseEntity<List<Thread>> getAllThreads(@RequestHeader("Authorization") String jwtToken) {
        Long id = userAuthenticationProvider.parseToken(jwtToken).getId();
        return ResponseEntity.ok(threadService.getAllUserThreads(id));
    }
}
