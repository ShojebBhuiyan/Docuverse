package com.docuverse.backend.repository;

import com.docuverse.backend.model.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
    FlashCard findByTitle(String title);
}
