package org.example.studynest.service;

import org.example.studynest.repository.FlashcardRepository;
import org.example.studynest.repository.FlashcardSetRepository;
import org.example.studynest.repository.FlashcardSetTopicRepository;
import org.example.studynest.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FlashcardSetService {
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
}
