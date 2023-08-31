package com.docuverse.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Thread")
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private long name;
    @Column(name = "vectorIndex")
    private long vectorIndex;
    @Column(name = "chatLog")
    private String chatLog;
    @ManyToOne
    @JoinColumn(name = "files")
    private File file;

}
