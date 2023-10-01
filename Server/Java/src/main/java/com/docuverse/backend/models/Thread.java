package com.docuverse.backend.models;


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
@Table(name = "Threads")
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thread_id")
    private long threadId;
    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "Threads", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<>();

}
