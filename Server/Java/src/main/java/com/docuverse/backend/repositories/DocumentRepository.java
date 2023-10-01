package com.docuverse.backend.repositories;

import com.docuverse.backend.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findDocumentByTitle(String title);
}
