package org.example.studynest.exception;

public class UserNotFoundById extends RuntimeException {
    public UserNotFoundById() {
        super("User with this id was not found");
    }
}
