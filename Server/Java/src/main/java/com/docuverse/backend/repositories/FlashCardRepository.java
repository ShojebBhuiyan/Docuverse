package com.docuverse.backend.repositories;

import com.docuverse.backend.models.Deck;
import com.docuverse.backend.models.FlashCard;
import com.docuverse.backend.models.Thread;
import com.docuverse.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
    FlashCard findByTitle(String title);
    List<FlashCard> findByDeck(Deck deck);
}
