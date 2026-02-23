package org.example.studynest.mapper;

import org.example.studynest.dto.response.TopicDTO;
import org.example.studynest.entity.FlashcardSetTopic;

import java.util.ArrayList;
import java.util.List;

public class TopicMapper {
    public static List<TopicDTO> toTopicDTO(List<FlashcardSetTopic> topicList) {
        List<TopicDTO> topicDTOList = new ArrayList<>();
        for (FlashcardSetTopic flashcardSetTopic : topicList) {
            TopicDTO topicDTO = new TopicDTO();
            topicDTO.setId(flashcardSetTopic.getId());
            topicDTO.setTopic(flashcardSetTopic.getTopic());
            topicDTOList.add(topicDTO);
        }
        return topicDTOList;
    }
}
