package org.example.studynest.service;

import jakarta.transaction.Transactional;
import org.example.studynest.dto.request.UpdateFlashcardDTO;
import org.example.studynest.dto.response.FlashcardDTO;
import org.example.studynest.entity.Flashcard;
import org.example.studynest.exception.FlashcardNotFound;
import org.example.studynest.repository.FlashcardRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FlashcardService {
    private final FlashcardRepository flashcardRepository;

    public FlashcardService(FlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    @Transactional
    public FlashcardDTO updateFlashcard(UUID flashcardId, UUID userId, UpdateFlashcardDTO updateFlashcardDTO) {
        Optional<Flashcard> updated = flashcardRepository.findByIdAndFlashcardSetUserId(flashcardId, userId);
        if (updated.isEmpty()) throw new FlashcardNotFound();

        if (updateFlashcardDTO.prompt() != null) {
            updated.get().setPrompt(updateFlashcardDTO.prompt());
        }

        if (updateFlashcardDTO.answer() != null) {
            updated.get().setAnswer(updateFlashcardDTO.answer());
        }
        return new FlashcardDTO(
                updated.get().getId(),
                updated.get().getPrompt(),
                updated.get().getAnswer(),
                updated.get().getOrderIndex()
        );
    }
}
