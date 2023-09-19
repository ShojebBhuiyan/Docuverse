package com.docuverse.backend.model;

import com.docuverse.backend.enums.Subscription;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = "User")
public class User implements UserDetails, GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @Column(name = "subscription")
    private Subscription subscription;


    public User() {

    }

    public User(String name, String email, String password, Subscription subscription) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.subscription = subscription;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getAuthority() {
        return this.subscription.toString();
    }

    public void setAuthority(Subscription authority) {
        this.subscription = authority;
    }

    public String getSubscription() {
        return this.subscription.toString();
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
}


