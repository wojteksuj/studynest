package org.example.studynest.service;

import org.example.studynest.dto.response.TopicDTO;
import org.example.studynest.entity.FlashcardSetTopic;
import org.example.studynest.mapper.TopicMapper;
import org.example.studynest.repository.FlashcardSetTopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class TopicService {
    public final FlashcardSetTopicRepository flashcardSetTopicRepository;

    public TopicService(FlashcardSetTopicRepository flashcardSetTopicRepository) {
        this.flashcardSetTopicRepository = flashcardSetTopicRepository;
    }

    public List<TopicDTO> getAllTopics() {
        List<FlashcardSetTopic> topicList =  flashcardSetTopicRepository.findAll();
        return TopicMapper.toTopicDTO(topicList);
    }
}
