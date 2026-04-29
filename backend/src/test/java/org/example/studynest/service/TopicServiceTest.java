package org.example.studynest.service;

import org.example.studynest.dto.request.CreateTopicDTO;
import org.example.studynest.dto.response.TopicDTO;
import org.example.studynest.entity.FlashcardSetTopic;
import org.example.studynest.entity.User;
import org.example.studynest.exception.UserNotFoundById;
import org.example.studynest.repository.FlashcardSetTopicRepository;
import org.example.studynest.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {

    @Mock
    private FlashcardSetTopicRepository flashcardSetTopicRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TopicService topicService;

    @Test
    void getAllTopicsByUser_returnsMappedDTOs() {
        UUID userId = UUID.randomUUID();
        User user = new User("alice", "alice@example.com", "hash");
        FlashcardSetTopic topic1 = new FlashcardSetTopic("Math", user);
        FlashcardSetTopic topic2 = new FlashcardSetTopic("Science", user);

        when(flashcardSetTopicRepository.findAllByUser_Id(userId)).thenReturn(List.of(topic1, topic2));

        List<TopicDTO> result = topicService.getAllTopicsByUser(userId);

        assertEquals(2, result.size());
        assertEquals("Math", result.get(0).getTopic());
        assertEquals("Science", result.get(1).getTopic());
    }

    @Test
    void getAllTopicsByUser_returnsEmptyList_whenNoTopics() {
        UUID userId = UUID.randomUUID();

        when(flashcardSetTopicRepository.findAllByUser_Id(userId)).thenReturn(List.of());

        List<TopicDTO> result = topicService.getAllTopicsByUser(userId);

        assertTrue(result.isEmpty());
    }

    @Test
    void createTopic_savesAndReturnsDTO() {
        UUID userId = UUID.randomUUID();
        User user = new User("alice", "alice@example.com", "hash");
        CreateTopicDTO dto = new CreateTopicDTO("History");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(flashcardSetTopicRepository.save(any(FlashcardSetTopic.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        TopicDTO result = topicService.createTopic(userId, dto);

        verify(flashcardSetTopicRepository).save(any(FlashcardSetTopic.class));
        assertEquals("History", result.getTopic());
    }

    @Test
    void createTopic_throws_whenUserNotFound() {
        UUID userId = UUID.randomUUID();
        CreateTopicDTO dto = new CreateTopicDTO("History");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundById.class, () -> topicService.createTopic(userId, dto));
    }
}
