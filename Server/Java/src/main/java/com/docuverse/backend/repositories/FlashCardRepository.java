package com.docuverse.backend.repositories;

import com.docuverse.backend.models.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
    FlashCard findByTitle(String title);
}
