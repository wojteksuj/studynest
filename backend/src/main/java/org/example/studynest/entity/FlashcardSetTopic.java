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

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    protected FlashcardSetTopic() {}

    public FlashcardSetTopic(String topic, User user){
        this.topic = topic;
        this.user = user;
    }

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

    public User getUser() {
        return user;
    }
}
