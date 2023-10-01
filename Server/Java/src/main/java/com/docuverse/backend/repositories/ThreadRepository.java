package com.docuverse.backend.repositories;

import com.docuverse.backend.models.Thread;
import com.docuverse.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {
    Thread findByTitle(String title);
    List<Thread> findByUser(User user);


//    List<Thread> findByUserId(Long userId);
}
