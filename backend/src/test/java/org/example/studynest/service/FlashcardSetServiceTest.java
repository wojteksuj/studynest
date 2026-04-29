package org.example.studynest.service;

import org.example.studynest.dto.request.CreateFlashcardDTO;
import org.example.studynest.dto.request.CreateFlashcardSetDTO;
import org.example.studynest.dto.response.FlashcardDTO;
import org.example.studynest.dto.response.FlashcardSetDTO;
import org.example.studynest.entity.Flashcard;
import org.example.studynest.entity.FlashcardSet;
import org.example.studynest.entity.FlashcardSetTopic;
import org.example.studynest.entity.User;
import org.example.studynest.exception.FlashcardSetNotFound;
import org.example.studynest.exception.FlashcardSetNotFoundById;
import org.example.studynest.exception.FlashcardSetTopicNotFoundById;
import org.example.studynest.exception.UserNotFoundById;
import org.example.studynest.repository.FlashcardRepository;
import org.example.studynest.repository.FlashcardSetRepository;
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
    void testCreateFlashcardSet(){
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
        assertEquals("Topic", newSet.getTopic());
    }

    @Test
    void testThrowUserNotFoundById(){
        UUID userId = UUID.randomUUID();
        UUID topicId = UUID.randomUUID();

        CreateFlashcardSetDTO newDto = new CreateFlashcardSetDTO();
        newDto.setTitle("New set");
        newDto.setDescription("New description");
        newDto.setTopicId(topicId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundById.class, () -> flashcardSetService.createFlashcardSet(newDto, userId));
    }

    @Test
    void testThrowFlashcardSetTopicNotFoundById(){
        UUID userId = UUID.randomUUID();
        UUID topicId = UUID.randomUUID();

        User user = new User("Username", "Email", "Password");

        CreateFlashcardSetDTO newDto = new CreateFlashcardSetDTO();
        newDto.setTitle("New set");
        newDto.setDescription("New description");
        newDto.setTopicId(topicId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(flashcardSetTopicRepository.findById(topicId)).thenReturn(Optional.empty());

        assertThrows(FlashcardSetTopicNotFoundById.class, () -> flashcardSetService.createFlashcardSet(newDto, userId));
    }

    @Test
    void getFlashcardSetById_throws_whenNotFound() {
        UUID setId = UUID.randomUUID();

        when(flashcardSetRepository.findById(setId)).thenReturn(Optional.empty());

        assertThrows(FlashcardSetNotFoundById.class, () -> flashcardSetService.getFlashcardSetById(setId));
    }

    @Test
    void getFlashcardSetById_returnsEntity_whenFound() {
        UUID setId = UUID.randomUUID();
        FlashcardSet flashcardSet = new FlashcardSet();

        when(flashcardSetRepository.findById(setId)).thenReturn(Optional.of(flashcardSet));

        FlashcardSet result = flashcardSetService.getFlashcardSetById(setId);

        assertEquals(flashcardSet, result);
    }

    @Test
    void deleteFlashcardSetById_deletesSet_whenOwned() {
        UUID setId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        FlashcardSet flashcardSet = new FlashcardSet();

        when(flashcardSetRepository.findFlashcardSetByIdAndUserId(setId, userId))
                .thenReturn(Optional.of(flashcardSet));

        flashcardSetService.deleteFlashcardSetById(setId, userId);

        verify(flashcardSetRepository).delete(flashcardSet);
    }

    @Test
    void deleteFlashcardSetById_throws_whenNotOwned() {
        UUID setId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        when(flashcardSetRepository.findFlashcardSetByIdAndUserId(setId, userId))
                .thenReturn(Optional.empty());

        assertThrows(FlashcardSetNotFound.class,
                () -> flashcardSetService.deleteFlashcardSetById(setId, userId));
    }

    @Test
    void createFlashcard_savesAndReturnsDTO() {
        UUID userId = UUID.randomUUID();
        UUID setId = UUID.randomUUID();
        FlashcardSet flashcardSet = new FlashcardSet();
        CreateFlashcardDTO dto = new CreateFlashcardDTO("What is Java?", "A programming language");

        when(flashcardSetRepository.findFlashcardSetByIdAndUserId(setId, userId))
                .thenReturn(Optional.of(flashcardSet));
        when(flashcardRepository.countAllByFlashcardSet(flashcardSet)).thenReturn(2);
        when(flashcardRepository.save(any(Flashcard.class))).thenAnswer(inv -> inv.getArgument(0));

        FlashcardDTO result = flashcardSetService.createFlashcard(userId, dto, setId);

        verify(flashcardRepository).save(any(Flashcard.class));
        assertEquals("What is Java?", result.prompt());
        assertEquals("A programming language", result.answer());
        assertEquals(3, result.orderIndex());
    }

    @Test
    void createFlashcard_throws_whenSetNotOwned() {
        UUID userId = UUID.randomUUID();
        UUID setId = UUID.randomUUID();
        CreateFlashcardDTO dto = new CreateFlashcardDTO("Q", "A");

        when(flashcardSetRepository.findFlashcardSetByIdAndUserId(setId, userId))
                .thenReturn(Optional.empty());

        assertThrows(FlashcardSetNotFound.class,
                () -> flashcardSetService.createFlashcard(userId, dto, setId));
    }

    @Test
    void getFlashcardsBySetId_throws_whenEmpty() {
        UUID setId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        when(flashcardRepository.findFlashcardsBySetId(setId, userId)).thenReturn(List.of());

        assertThrows(FlashcardSetNotFound.class,
                () -> flashcardSetService.getFlashcardsBySetId(setId, userId));
    }

    @Test
    void getFlashcardsBySetId_returnsList_whenFound() {
        UUID setId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        FlashcardDTO flashcardDTO = new FlashcardDTO(UUID.randomUUID(), "Q", "A", 1);

        when(flashcardRepository.findFlashcardsBySetId(setId, userId)).thenReturn(List.of(flashcardDTO));

        List<FlashcardDTO> result = flashcardSetService.getFlashcardsBySetId(setId, userId);

        assertEquals(1, result.size());
        assertEquals("Q", result.get(0).prompt());
    }
}
