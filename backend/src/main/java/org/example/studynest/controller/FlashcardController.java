package org.example.studynest.controller;

import org.example.studynest.dto.request.UpdateFlashcardDTO;
import org.example.studynest.dto.response.FlashcardDTO;
import org.example.studynest.security.CustomUserDetails;
import org.example.studynest.service.FlashcardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/flashcards")
public class FlashcardController {
    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PutMapping("/{flashcardId}")
    public ResponseEntity<FlashcardDTO> updateFlashcard(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UpdateFlashcardDTO updateFlashcardDTO,
            @PathVariable UUID flashcardId){
        FlashcardDTO updated = flashcardService.updateFlashcard(flashcardId, userDetails.getId(), updateFlashcardDTO);
        return ResponseEntity.ok(updated);
    }
}
