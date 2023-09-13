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
//    @ManyToOne
//    @JoinColumn(name = "threads")
//    private Thread thread;

    public User() {

    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
//        this.thread = thread;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

//package com.docuverse.backend.model;
//
//        import jakarta.persistence.*;
//
//@Entity
//@Table(name = "Thread")
//public class Thread {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//    @Column(name = "name")
//    private long name;
//    @Column(name = "vectorIndex")
//    private long vectorIndex;
//    @Column(name = "chatLog")
//    private String chatLog;
//    @ManyToOne
//    @JoinColumn(name = "files")
//    private File file;
//
//}


//package com.docuverse.backend.model;
//
//        import jakarta.persistence.*;
//
//@Entity
//@Table(name = "file")
//public class File {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//    @Column(name = "name")
//    private String name;
//    @Column(name = "url")
//    private String url;
//    @Column(name = "textContent")
//    private String textContent;
//}


