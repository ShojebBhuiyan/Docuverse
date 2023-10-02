package com.docuverse.backend.services;

import com.docuverse.backend.exceptions.AppException;
import com.docuverse.backend.models.Deck;
import com.docuverse.backend.models.FlashCard;
import com.docuverse.backend.repositories.DeckRepository;
import com.docuverse.backend.repositories.FlashCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FlashCardService {
    private final DeckRepository deckRepository;
    private final FlashCardRepository flashCardRepository;

    List<FlashCard> getAllDeckFlashCards(Long deckId) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new AppException("Unknown deck", HttpStatus.NOT_FOUND));
        return flashCardRepository.findByDeck(deck);
    }

    List<FlashCard> createFlashCard(Long deckId) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new AppException("Unknown deck", HttpStatus.NOT_FOUND));

        FlashCard flashCard = new FlashCard();

        flashCard.setDeck(deck);
        flashCard.setTitle("New Flashcard");
        flashCardRepository.save(flashCard);

        return flashCardRepository.findByDeck(deck);
    }
}
