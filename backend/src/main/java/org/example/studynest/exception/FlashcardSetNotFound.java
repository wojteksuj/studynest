package org.example.studynest.exception;

public class FlashcardSetNotFound extends RuntimeException{
    public FlashcardSetNotFound() {
        super("Set not found.");
    }
}
