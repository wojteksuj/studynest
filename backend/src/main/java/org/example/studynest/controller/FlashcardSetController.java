package org.example.studynest.controller;

import jakarta.validation.Valid;
import org.example.studynest.dto.request.CreateFlashcardDTO;
import org.example.studynest.dto.request.CreateFlashcardSetDTO;
import org.example.studynest.dto.request.UpdateFlashcardDTO;
import org.example.studynest.dto.response.FlashcardDTO;
import org.example.studynest.dto.response.FlashcardSetDTO;
import org.example.studynest.security.CustomUserDetails;
import org.example.studynest.service.FlashcardSetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{setId}/flashcards")
    public ResponseEntity<List<FlashcardDTO>> getFlashcards(
            @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID setId) {
        List<FlashcardDTO> flashcardDTOList = flashcardSetService.getFlashcardsBySetId(setId, userDetails.getId());
        return ResponseEntity.ok(flashcardDTOList);
    }

    @GetMapping("/{setId}")
    public ResponseEntity<FlashcardSetDTO> getFlashcardSetById(
            @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID setId) {
        return ResponseEntity.ok(flashcardSetService.getFlashcardSetById(setId, userDetails.getId()));
    }

    @DeleteMapping("/{setId}")
    public ResponseEntity<Void> deleteFlashcardSet(
            @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID setId) {
        flashcardSetService.deleteFlashcardSetById(setId, userDetails.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{setId}/flashcards")
    public ResponseEntity<FlashcardDTO> createFlashcard(
            @AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody CreateFlashcardDTO createFlashcardDTO, @PathVariable UUID setId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(flashcardSetService.createFlashcard(
                userDetails.getId(),
                createFlashcardDTO,
                setId
        ));
    }
}
