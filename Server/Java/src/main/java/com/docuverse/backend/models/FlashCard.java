package com.docuverse.backend.models;

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

    @Column(name = "weight") // New field for weight
    private int weight = 3; // Set weight to 3 upon generation

    public FlashCard() {

    }

    public FlashCard(String title, String question, String answer) {
        this.title = title;
        this.question = question;
        this.answer = answer;
        // Weight is set to 3 upon generation
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
