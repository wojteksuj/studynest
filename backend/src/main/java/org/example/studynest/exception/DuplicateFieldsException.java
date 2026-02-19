package org.example.studynest.exception;

public class DuplicateFieldsException extends RuntimeException {
    String field;

    public DuplicateFieldsException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
