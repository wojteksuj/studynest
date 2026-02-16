package org.example.studynest.exception;

public class UsernameNotFoundByEmailException extends RuntimeException {
    public UsernameNotFoundByEmailException(String email) {
        super("User with provided email: " + email + " does not exist");
    }
}
