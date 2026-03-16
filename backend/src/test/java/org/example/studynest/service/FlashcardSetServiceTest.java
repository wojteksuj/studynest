package org.example.studynest.service;

import org.example.studynest.dto.request.CreateFlashcardSetDTO;
import org.example.studynest.dto.response.FlashcardSetDTO;
import org.example.studynest.entity.FlashcardSet;
import org.example.studynest.entity.FlashcardSetTopic;
import org.example.studynest.entity.User;
import org.example.studynest.exception.UserNotFoundById;
import org.example.studynest.repository.FlashcardRepository;
import org.example.studynest.repository.FlashcardSetRepository;
import org.example.studynest.repository.FlashcardSetTopicRepository;
import org.example.studynest.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlashcardSetServiceTest {
    @Mock
    private FlashcardSetRepository flashcardSetRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FlashcardSetTopicRepository flashcardSetTopicRepository;
    @Mock
    private FlashcardRepository flashcardRepository;

    @InjectMocks
    private FlashcardSetService flashcardSetService;

    @Test
    void shouldCreateFlashcardSet(){
        UUID userId = UUID.randomUUID();
        UUID topicId = UUID.randomUUID();

        User user = new User("Username", "Email", "Password");
        FlashcardSetTopic fsTopic = new FlashcardSetTopic("Topic", user);

        CreateFlashcardSetDTO newDto = new CreateFlashcardSetDTO();
        newDto.setTitle("New set");
        newDto.setDescription("New description");
        newDto.setTopicId(topicId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(flashcardSetTopicRepository.findById(newDto.getTopicId())).thenReturn(Optional.of(fsTopic));

        FlashcardSetDTO newSet = flashcardSetService.createFlashcardSet(newDto, userId);
        verify(flashcardSetRepository).save(any(FlashcardSet.class));

        assertEquals("New set", newSet.getTitle());
        assertEquals("New description", newSet.getDescription());
    }

    @Test
    void shouldThrowUserNotFoundById(){
        UUID userId = UUID.randomUUID();
        UUID topicId = UUID.randomUUID();

        CreateFlashcardSetDTO newDto = new CreateFlashcardSetDTO();
        newDto.setTitle("New set");
        newDto.setDescription("New description");
        newDto.setTopicId(topicId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundById.class, () -> flashcardSetService.createFlashcardSet(newDto, userId));
    }


}
