package com.docuverse.backend.repositories;

import com.docuverse.backend.models.Deck;
import com.docuverse.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
    List<Deck> findByUser(User user);
}
