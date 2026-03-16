package org.example.studynest.service;

import jakarta.transaction.Transactional;
import org.example.studynest.dto.request.CreateFlashcardDTO;
import org.example.studynest.dto.request.CreateFlashcardSetDTO;
import org.example.studynest.dto.response.FlashcardDTO;
import org.example.studynest.dto.response.FlashcardSetDTO;
import org.example.studynest.entity.Flashcard;
import org.example.studynest.entity.FlashcardSet;
import org.example.studynest.exception.FlashcardSetNotFound;
import org.example.studynest.exception.FlashcardSetNotFoundById;
import org.example.studynest.exception.FlashcardSetTopicNotFoundById;
import org.example.studynest.exception.UserNotFoundById;
import org.example.studynest.repository.FlashcardRepository;
import org.example.studynest.repository.FlashcardSetRepository;
import org.example.studynest.repository.FlashcardSetTopicRepository;
import org.example.studynest.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FlashcardSetService {
    private final FlashcardSetRepository flashcardSetRepository;
    private final UserRepository userRepository;
    private final FlashcardSetTopicRepository flashcardSetTopicRepository;
    private final FlashcardRepository flashcardRepository;

    public FlashcardSetService(FlashcardSetRepository flashcardSetRepository, UserRepository userRepository,
                               FlashcardSetTopicRepository flashcardSetTopicRepository, FlashcardRepository flashcardRepository) {
        this.flashcardSetRepository = flashcardSetRepository;
        this.userRepository = userRepository;
        this.flashcardSetTopicRepository = flashcardSetTopicRepository;
        this.flashcardRepository = flashcardRepository;
    }

    public FlashcardSet getFlashcardSetById(UUID id) {
        return flashcardSetRepository.findById(id).orElseThrow(FlashcardSetNotFoundById::new);
    }

    public List<FlashcardSetDTO> getUserFlashcardSets(UUID id) {
        return flashcardSetRepository.findAllById(id);
    }

    @Transactional
    public FlashcardSetDTO createFlashcardSet(CreateFlashcardSetDTO flashcardSetDTO, UUID userId) {
        FlashcardSet newFlashcardSet = new FlashcardSet();
        newFlashcardSet.setTitle(flashcardSetDTO.getTitle());
        newFlashcardSet.setDescription(flashcardSetDTO.getDescription());
        newFlashcardSet.setUser(userRepository.findById(userId).orElseThrow(UserNotFoundById::new));
        newFlashcardSet.setFlashcardSetTopic(flashcardSetTopicRepository.findById(flashcardSetDTO.getTopicId()).orElseThrow(FlashcardSetTopicNotFoundById::new));
        flashcardSetRepository.save(newFlashcardSet);

        FlashcardSetDTO dto = new FlashcardSetDTO(newFlashcardSet.getId(), newFlashcardSet.getTitle(), newFlashcardSet.getDescription(), newFlashcardSet.getFlashcardSetTopic().getTopic());
        return dto;
    }

    public List<FlashcardDTO> getFlashcardsBySetId(UUID setId, UUID userId) {
        List<FlashcardDTO> flashcardDTOList = flashcardRepository.findFlashcardsBySetId(setId, userId);
        if (flashcardDTOList.isEmpty()) throw new FlashcardSetNotFound();
        return flashcardDTOList;
    }

    public FlashcardSetDTO getFlashcardSetById(UUID id, UUID userId) {
        return flashcardSetRepository.findFlashcardSetById(id, userId).orElseThrow(FlashcardSetNotFound::new);
    }

    @Transactional
    public void deleteFlashcardSetById(UUID setId, UUID userId) {
        Optional<FlashcardSet> deletedSet = flashcardSetRepository.findFlashcardSetByIdAndUserId(setId, userId);
        if (deletedSet.isEmpty()) throw new FlashcardSetNotFound();
        flashcardSetRepository.delete(deletedSet.get());
    }

    @Transactional
    public FlashcardDTO createFlashcard(UUID userId, CreateFlashcardDTO createFlashcardDTO, UUID setId) {
        Optional<FlashcardSet> flashcardSet = flashcardSetRepository.findFlashcardSetByIdAndUserId(setId, userId);
        if (flashcardSet.isEmpty()) throw new FlashcardSetNotFound();

        int maxOrder = flashcardRepository.countAllByFlashcardSet(flashcardSet.get());
        int orderIndex = maxOrder + 1;

        Flashcard newFlashcard = new Flashcard(
                createFlashcardDTO.prompt(),
                createFlashcardDTO.answer(),
                orderIndex,
                flashcardSet.get()
        );

        flashcardRepository.save(newFlashcard);

        return new FlashcardDTO(
                newFlashcard.getId(),
                newFlashcard.getPrompt(),
                newFlashcard.getAnswer(),
                newFlashcard.getOrderIndex()
        );
    }


}
