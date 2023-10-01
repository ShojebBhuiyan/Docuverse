package com.docuverse.backend.models;

import com.docuverse.backend.enums.MessageRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageId")
    private long id;

    @Column(name = "content")
    private String content;

    @Column(name = "role")
    private MessageRole role;

    @Column(name = "datetime")
    private DateTime dateTime;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "threadId")
    private Thread thread;
}
