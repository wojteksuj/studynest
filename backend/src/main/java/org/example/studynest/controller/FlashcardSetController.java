package org.example.studynest.controller;
import org.example.studynest.dto.response.FlashcardSetDTO;
import org.example.studynest.security.CustomUserDetails;
import org.example.studynest.service.FlashcardSetService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flashcard-sets")
public class FlashcardSetController {
    private final FlashcardSetService flashcardSetService;

    public FlashcardSetController(FlashcardSetService flashcardSetService) {
        this.flashcardSetService = flashcardSetService;
    }

    @GetMapping
    public List<FlashcardSetDTO> getUserFlashcardSets(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return flashcardSetService.getUserFlashcardSets(userDetails.getId());
    }
}
