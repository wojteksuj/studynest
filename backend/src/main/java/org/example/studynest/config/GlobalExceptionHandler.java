package org.example.studynest.config;

import org.example.studynest.dto.response.errors.DuplicateFieldErrorResponse;
import org.example.studynest.exception.DuplicateFieldsException;
import org.example.studynest.exception.FlashcardSetNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {

        List<Map<String, String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", error.getDefaultMessage()
                ))
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("errors", errors));
    }

    @ExceptionHandler(DuplicateFieldsException.class)
    public ResponseEntity<DuplicateFieldErrorResponse> handleDuplicateField(DuplicateFieldsException ex) {

        DuplicateFieldErrorResponse response =
                new DuplicateFieldErrorResponse(ex.getField(), ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(FlashcardSetNotFound.class)
    public ResponseEntity<Void> handleNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}