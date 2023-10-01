package com.docuverse.backend.repositories;

import com.docuverse.backend.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Document findDocumentByTitle(String title);
}
