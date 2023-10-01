package com.docuverse.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "FlashCard")
public class FlashCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "question")
    private String question;
    @Column(name = "answer")
    private String answer;

    @Column(name = "weight")
    private int weight;

    public FlashCard() {

    }

    public FlashCard(String title, String question, String answer, int weight) {
        this.title = title;
        this.question = question;
        this.answer = answer;
        setWeight(weight); // Use the setter to ensure validation
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setWeight(int weight) {
        // Validate that weight is within the range [0, 3]
        if (weight < 0 || weight > 3) {
            throw new IllegalArgumentException("Weight must be between 0 and 3");
        }
        this.weight = weight;
    }
}
