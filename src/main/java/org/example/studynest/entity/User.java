package org.example.studynest.entity;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false)
    private UUID id;

    @Setter
    @Column(nullable = false, unique = true)
    private String username;

    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "registration_date", nullable = false)
    private Instant registrationDate;

    protected User() {
    }

    public User(String username, String email, String passwordHash) {
        this.id = UUID.randomUUID();
        this.registrationDate = Instant.now();
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (registrationDate == null) {
            registrationDate = Instant.now();
        }
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

}
