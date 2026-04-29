package org.example.studynest.exception;

public class FlashcardSetNotFoundById extends RuntimeException {
    public FlashcardSetNotFoundById() {
        super("Flashcard set with provided id does not exist.");
    }
}
