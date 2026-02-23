package org.example.studynest.exception;

public class FlashcardSetTopicNotFoundById extends RuntimeException {
    public FlashcardSetTopicNotFoundById() {
        super("Topic with this id was not found");
    }
}
