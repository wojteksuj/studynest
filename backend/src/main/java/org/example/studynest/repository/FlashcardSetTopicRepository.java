package org.example.studynest.repository;

import org.example.studynest.entity.FlashcardSetTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlashcardSetTopicRepository extends JpaRepository<FlashcardSetTopic, UUID> {
}
