package org.example.studynest.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateFlashcardDTO(
        @NotBlank
        String prompt,
        @NotBlank
        String answer) {
}
