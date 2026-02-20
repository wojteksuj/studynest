package org.example.studynest.service;

import org.example.studynest.dto.response.FlashcardSetDTO;
import org.example.studynest.entity.FlashcardSet;
import org.example.studynest.entity.User;
import org.example.studynest.exception.FlashcardSetNotFoundById;
import org.example.studynest.repository.FlashcardSetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FlashcardSetService {
    private final FlashcardSetRepository flashcardSetRepository;

    public FlashcardSetService(FlashcardSetRepository flashcardSetRepository) {
        this.flashcardSetRepository = flashcardSetRepository;
    }

    public FlashcardSet getFlashcardSetById(UUID id) {
        return flashcardSetRepository.findById(id).orElseThrow(FlashcardSetNotFoundById::new);
    }

    public List<FlashcardSetDTO> getUserFlashcardSets(UUID id) {
        return flashcardSetRepository.findAllById(id);
    }
}
