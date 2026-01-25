package org.example.studynest.entity;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="flashcards")
public class Flashcard {

    @Id
    @Column(nullable=false, updatable = false)
    private UUID id;

    @Setter
    @Column(nullable = false)
    private String prompt;

    @Setter
    @Column(nullable = false)
    private String answer;

    @Setter
    @Column(nullable = false)
    private Integer orderIndex;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="flashcard_set_id", nullable = false)
    private FlashcardSet flashcardSet;

    protected Flashcard() {}

    @PrePersist
    protected void onCreate() {
        if(id == null) {
            id = UUID.randomUUID();
        }
    }

    public UUID getId() {
        return id;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getAnswer() {
        return answer;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public FlashcardSet getFlashcardSet() {
        return flashcardSet;
    }
}
