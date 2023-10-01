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

    public List<Thread> createThread(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        Thread thread = new Thread();
        thread.setUser(user);
        thread.setTitle("New Thread");
        threadRepository.save(thread);

        return threadRepository.findByUser(user);
    }

    public List<Thread> updateThreadTitle(Long userId, Long threadId, String title) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        Thread thread = threadRepository.findById(threadId).get();
        thread.setTitle(title);
        threadRepository.save(thread);

        return threadRepository.findByUser(user);
    }
}
