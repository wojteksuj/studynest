package org.example.studynest.entity;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="flashcard_sets")
public class FlashcardSet {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false)
    private String description;

    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Column(name = "last_modified_at", nullable = false)
    private Instant lastModifiedAt;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="flashcard_set_topic_id", nullable = false)
    private FlashcardSetTopic flashcardSetTopic;

    public FlashcardSet() {}

    @PrePersist
    protected void onCreate() {
        if(id == null) {
            id = UUID.randomUUID();
        }
        if(creationDate == null) {
            creationDate = Instant.now();
        }
        if(lastModifiedAt == null) {
            lastModifiedAt = creationDate;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Instant getLastModifiedAt() {
        return lastModifiedAt;
    }

    public User getUser() {
        return user;
    }

    public FlashcardSetTopic getFlashcardSetTopic() {
        return flashcardSetTopic;
    }
}
