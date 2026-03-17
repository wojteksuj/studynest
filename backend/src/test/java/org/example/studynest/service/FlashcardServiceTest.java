package org.example.studynest.service;

import org.example.studynest.dto.request.UpdateFlashcardDTO;
import org.example.studynest.dto.response.FlashcardDTO;
import org.example.studynest.entity.Flashcard;
import org.example.studynest.entity.FlashcardSet;
import org.example.studynest.exception.FlashcardNotFound;
import org.example.studynest.repository.FlashcardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlashcardServiceTest {
    @Mock
    private FlashcardRepository flashcardRepository;
    @InjectMocks
    private FlashcardService flashcardService;

    @Test
    void testUpdateFlashcard() {
        UUID flashcardId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Flashcard flashcard = new Flashcard("Old prompt", "Old answer", 1, new FlashcardSet());
        UpdateFlashcardDTO updateDto = new UpdateFlashcardDTO("New prompt", "New answer");

        when(flashcardRepository.findByIdAndFlashcardSetUserId(flashcardId, userId))
                .thenReturn(Optional.of(flashcard));

        FlashcardDTO updated = flashcardService.updateFlashcard(flashcardId, userId, updateDto);
        assertEquals("New prompt", updated.prompt());
        assertEquals("New answer", updated.answer());
    }

    @Test
    void testUpdateFlashcardOnlyPrompt() {
        UUID flashcardId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Flashcard flashcard = new Flashcard("Old prompt", "Old answer", 1, new FlashcardSet());
        UpdateFlashcardDTO updateDto = new UpdateFlashcardDTO("New prompt", null);

        when(flashcardRepository.findByIdAndFlashcardSetUserId(flashcardId, userId))
                .thenReturn(Optional.of(flashcard));

        FlashcardDTO updated = flashcardService.updateFlashcard(flashcardId, userId, updateDto);
        assertEquals("New prompt", updated.prompt());
        assertEquals("Old answer", updated.answer());
    }

    @Test
    void testUpdateFlashcardOnlyAnswer() {
        UUID flashcardId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Flashcard flashcard = new Flashcard("Old prompt", "Old answer", 1, new FlashcardSet());
        UpdateFlashcardDTO updateDto = new UpdateFlashcardDTO(null, "New answer");

        when(flashcardRepository.findByIdAndFlashcardSetUserId(flashcardId, userId))
                .thenReturn(Optional.of(flashcard));

        FlashcardDTO updated = flashcardService.updateFlashcard(flashcardId, userId, updateDto);
        assertEquals("Old prompt", updated.prompt());
        assertEquals("New answer", updated.answer());
    }

    @Test
    void testThrowFlashcardNotFound() {
        UUID flashcardId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        when(flashcardRepository.findByIdAndFlashcardSetUserId(flashcardId, userId))
                .thenReturn(Optional.empty());
        UpdateFlashcardDTO updateDto = new UpdateFlashcardDTO("A", "B");
        assertThrows(FlashcardNotFound.class, () -> flashcardService.updateFlashcard(flashcardId, userId, updateDto));
    }


}
