package org.example.studynest.exception;

import java.util.UUID;

public class FlashcardSetNotFoundById extends RuntimeException {
    public FlashcardSetNotFoundById() {
        super("Flashcard set with provided id does not exist.");
    }
}
