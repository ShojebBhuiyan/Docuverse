package com.docuverse.backend.repositories;

import com.docuverse.backend.models.Document;
import com.docuverse.backend.models.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findDocumentByTitle(String title);
    List<Document> findByThread(Thread thread);
}
