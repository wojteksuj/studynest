package org.example.studynest.controller;
import org.example.studynest.dto.request.CreateFlashcardSetDTO;
import org.example.studynest.dto.response.FlashcardSetDTO;
import org.example.studynest.security.CustomUserDetails;
import org.example.studynest.service.FlashcardSetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<FlashcardSetDTO> addFlashcardSet(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CreateFlashcardSetDTO flashcardSetDTO) {
            FlashcardSetDTO newFlashcard = flashcardSetService.createFlashcardSet(flashcardSetDTO, userDetails.getId());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(newFlashcard);
    }
}
