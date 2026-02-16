package org.example.studynest.entity;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="flashcard_set_topics")
public class FlashcardSetTopic {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Setter
    @Column(nullable = false)
    private String topic;

    protected FlashcardSetTopic() {}

    @PrePersist
    protected void onCreate() {
        if(id == null) {
            id = UUID.randomUUID();
        }
    }

    public UUID getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }
}
