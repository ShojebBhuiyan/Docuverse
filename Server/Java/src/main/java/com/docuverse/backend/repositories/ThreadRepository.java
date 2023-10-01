package com.docuverse.backend.repositories;

import com.docuverse.backend.models.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {
    Thread findByTitle(String title);
}
