package org.example.studynest.exception;

public class FlashcardNotFound extends RuntimeException {
    public FlashcardNotFound() {
        super("Flashcard not found.");
    }
}
