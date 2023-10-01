package com.docuverse.backend.repositories;

import com.docuverse.backend.models.Message;
import com.docuverse.backend.models.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByThread(Thread thread);
}
