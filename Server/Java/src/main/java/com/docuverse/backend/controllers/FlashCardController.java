package com.docuverse.backend.controllers;

import com.docuverse.backend.exceptions.ResourceNotFoundException;
import com.docuverse.backend.models.FlashCard;
import com.docuverse.backend.repositories.FlashCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/flashcard")
@CrossOrigin(origins = "http://localhost:3000")
public class FlashCardController {
    @Autowired
    private FlashCardRepository flashCardRepository;
    @GetMapping("/{id}")
    public ResponseEntity<FlashCard> getFlashCardById(@PathVariable Long id) {
        FlashCard flashCard = flashCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flashcard not found!"));
        return ResponseEntity.ok(flashCard);
    }

    @GetMapping("/all")
    public List<FlashCard> getAllFlashCards() {
        return flashCardRepository.findAll();
    }

    @PostMapping("/add")
    public FlashCard createFlashCard(@RequestBody FlashCard flashCard) {
        return flashCardRepository.save(flashCard);
    }

    @PutMapping("/update")
    public ResponseEntity<FlashCard> updateFlashCard(@RequestBody FlashCard flashCardInfo) {
        FlashCard flashCard = flashCardRepository.findById(flashCardInfo.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Flashcard not found!"));
        flashCard.setTitle(flashCardInfo.getTitle());
        flashCard.setQuestion(flashCardInfo.getQuestion());
        flashCard.setAnswer(flashCardInfo.getAnswer());

        FlashCard updatedFlashCard = flashCardRepository.save(flashCard);

        return ResponseEntity.ok(updatedFlashCard);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Boolean>> deleteFlashCard(@RequestBody FlashCard target) {
        FlashCard flashCard = flashCardRepository.findById(target.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Flashcard not found!"));

        flashCardRepository.delete(flashCard);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);

        return ResponseEntity.ok(response);
    }
}
