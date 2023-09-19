package com.docuverse.backend.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "Subscription")
public class Subscription implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "subscription")
    private String subscription;

    public Subscription() {
        super();
    }

    public Subscription(String subscription) {
        this.subscription = subscription;
    }
    @Override
    public String getAuthority() {
        return this.subscription;
    }
    public void setAuthority(String authority) {
        this.subscription = authority;
    }


}
