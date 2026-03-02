package org.example.studynest.service;

import org.example.studynest.dto.response.TopicDTO;
import org.example.studynest.entity.FlashcardSetTopic;
import org.example.studynest.mapper.TopicMapper;
import org.example.studynest.repository.FlashcardSetTopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TopicService {
    public final FlashcardSetTopicRepository flashcardSetTopicRepository;

    public TopicService(FlashcardSetTopicRepository flashcardSetTopicRepository) {
        this.flashcardSetTopicRepository = flashcardSetTopicRepository;
    }

    public List<TopicDTO> getAllTopicsByUser(UUID userId) {
        List<FlashcardSetTopic> topicList =  flashcardSetTopicRepository.findAllByUser_Id(userId);
        return TopicMapper.toTopicDTO(topicList);
    }
}
