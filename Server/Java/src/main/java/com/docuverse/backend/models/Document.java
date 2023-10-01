package com.docuverse.backend.models;

import jakarta.persistence.*;

import java.net.URL;

@Entity
@Table(name = "Documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private URL url;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    private Thread thread;

}
