package org.example.studynest.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email){
        super("User with provided email: " + email + " already exists");
    }
}
