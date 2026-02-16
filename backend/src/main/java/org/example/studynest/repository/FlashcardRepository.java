package org.example.studynest.repository;

import org.example.studynest.entity.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlashcardRepository extends JpaRepository<Flashcard, UUID> {
}
