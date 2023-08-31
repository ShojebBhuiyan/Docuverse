package com.docuverse.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @ManyToOne
    @JoinColumn(name = "threads")
    private Thread thread;

    public User() {

    }

    public User(long id, String name, String email, String password, Thread thread) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.thread = thread;
    }


}
