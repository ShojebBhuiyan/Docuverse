package com.docuverse.backend.models;

public class ChatRequest {
    private String question;


    // Constructors, getters, and setters

    public ChatRequest() {
        // Default constructor
    }

    public ChatRequest(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    @Override
    public String toString() {
        return "ChatRequest{" +
                "question='" + question + '\'' +
                '}';
    }
}

