package com.docuverse.backend.services;

import com.docuverse.backend.exceptions.AppException;
import com.docuverse.backend.models.Deck;
import com.docuverse.backend.models.User;
import com.docuverse.backend.repositories.DeckRepository;
import com.docuverse.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeckService {
    private final UserRepository userRepository;
    private final DeckRepository deckRepository;

    public List<Deck> getAllUserDecks(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        return deckRepository.findByUser(user);
    }

    public List<Deck> createDeck(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        Deck deck = new Deck();

        deck.setUser(user);
        deck.setTitle("New Deck");

        deckRepository.save(deck);

        return deckRepository.findByUser(user);
    }
}
