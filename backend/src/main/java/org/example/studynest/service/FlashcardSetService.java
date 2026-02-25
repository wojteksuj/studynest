package org.example.studynest.service;

import org.example.studynest.dto.request.CreateFlashcardSetDTO;
import org.example.studynest.dto.response.FlashcardDTO;
import org.example.studynest.dto.response.FlashcardSetDTO;
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

import java.util.List;
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

    public FlashcardSetDTO createFlashcardSet(CreateFlashcardSetDTO flashcardSetDTO, UUID userId) {
        FlashcardSet newFlashcardSet = new FlashcardSet();
        newFlashcardSet.setTitle(flashcardSetDTO.getTitle());
        newFlashcardSet.setDescription(flashcardSetDTO.getDescription());
        newFlashcardSet.setUser(userRepository.findById(userId).orElseThrow(UserNotFoundById::new));
        newFlashcardSet.setFlashcardSetTopic(flashcardSetTopicRepository.findById(flashcardSetDTO.getTopicId()).orElseThrow(FlashcardSetTopicNotFoundById::new));
        flashcardSetRepository.save(newFlashcardSet);

        FlashcardSetDTO dto = new FlashcardSetDTO(newFlashcardSet.getId(), newFlashcardSet.getTitle());
        return dto;
    }

    public List<FlashcardDTO> getFlashcardsBySetId(UUID setId, UUID userId){
        List<FlashcardDTO> flashcardDTOList = flashcardRepository.findFlashcardsBySetId(setId, userId);
        if(flashcardDTOList.isEmpty()) throw new FlashcardSetNotFound();
        return flashcardDTOList;
    }





}
