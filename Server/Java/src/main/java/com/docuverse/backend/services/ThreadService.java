package com.docuverse.backend.services;

import com.docuverse.backend.exceptions.AppException;
import com.docuverse.backend.models.Thread;
import com.docuverse.backend.models.User;
import com.docuverse.backend.repositories.ThreadRepository;
import com.docuverse.backend.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThreadService {
    private final UserRepository userRepository;
    private final ThreadRepository threadRepository;

    public ThreadService(UserRepository userRepository, ThreadRepository threadRepository) {
        this.userRepository = userRepository;
        this.threadRepository = threadRepository;
    }

    public List<Thread> getAllUserThreads(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        System.out.println("Finding user");
//        List<Thread> threadList = threadRepository.findByUser(user);
//        System.out.println("Thread List: " + threadList);
//        List<Thread> threadList = new ArrayList<>();

//        threadRepository.findByUser(user).forEach(threadList::add);
        return threadRepository.findByUser(user);
    }
}
