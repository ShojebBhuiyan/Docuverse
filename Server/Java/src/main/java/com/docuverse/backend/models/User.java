package com.docuverse.backend.models;

import com.docuverse.backend.enums.Subscription;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private long userId;
    @Column(name = "name")
    private String name;
    @Column(name = "email", unique = true)

    private String email;
    @Column(name = "password")
    private String password;

    @Column(name = "subscription")
    private Subscription subscription;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Thread> threads = new ArrayList<>();

}


