package org.example.studynest.service;

import org.example.studynest.dto.request.CreateTopicDTO;
import org.example.studynest.dto.response.TopicDTO;
import org.example.studynest.entity.Flashcard;
import org.example.studynest.entity.FlashcardSetTopic;
import org.example.studynest.exception.UserNotFoundById;
import org.example.studynest.mapper.TopicMapper;
import org.example.studynest.repository.FlashcardSetTopicRepository;
import org.example.studynest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TopicService {
    private final FlashcardSetTopicRepository flashcardSetTopicRepository;
    private final UserRepository userRepository;

    public TopicService(FlashcardSetTopicRepository flashcardSetTopicRepository, UserRepository userRepository) {
        this.flashcardSetTopicRepository = flashcardSetTopicRepository;
        this.userRepository = userRepository;
    }

    public List<TopicDTO> getAllTopicsByUser(UUID userId) {
        List<FlashcardSetTopic> topicList =  flashcardSetTopicRepository.findAllByUser_Id(userId);
        return TopicMapper.toTopicDTO(topicList);
    }

    public TopicDTO createTopic(UUID userId, CreateTopicDTO createTopicDTO){
        FlashcardSetTopic created = new FlashcardSetTopic(
                createTopicDTO.topic(),
                userRepository.findById(userId).orElseThrow(UserNotFoundById::new)
        );
        flashcardSetTopicRepository.save(created);
        TopicDTO topicDTO = new TopicDTO();
        topicDTO.setId(created.getId());
        topicDTO.setTopic(created.getTopic());
        topicDTO.setUserId(created.getUser().getId());
        return topicDTO;
    }
}
