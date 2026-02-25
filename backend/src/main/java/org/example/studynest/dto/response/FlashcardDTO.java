package org.example.studynest.dto.response;

import java.util.UUID;

public record FlashcardDTO(UUID id, String prompt, String answer, int orderIndex) {

}
