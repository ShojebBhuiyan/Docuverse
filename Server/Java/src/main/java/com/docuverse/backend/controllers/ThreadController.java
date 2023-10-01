package com.docuverse.backend.controllers;

import com.docuverse.backend.configuration.UserAuthenticationProvider;
import com.docuverse.backend.dtos.UpdateThreadDTO;
import com.docuverse.backend.models.Thread;
import com.docuverse.backend.services.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("Got id: " + id);

        return ResponseEntity.ok(threadService.getAllUserThreads(id));
    }
    @PostMapping("/create")
    public ResponseEntity<List<Thread>> createThread(@RequestHeader("Authorization") String jwtToken) {
        Long id = userAuthenticationProvider.parseToken(jwtToken).getId();

        return ResponseEntity.ok(threadService.createThread(id));
    }

    @PostMapping("/update-title")
    public ResponseEntity<List<Thread>> updateThreadTitle(@RequestHeader("Authorization") String jwtToken, @RequestBody UpdateThreadDTO updateThreadDTO) {
        Long id = userAuthenticationProvider.parseToken(jwtToken).getId();

        return ResponseEntity.ok(threadService.updateThreadTitle(id, updateThreadDTO.id(), updateThreadDTO.title()));
    }
}
