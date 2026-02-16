package org.example.studynest.repository;

import org.example.studynest.entity.FlashcardSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlashcardSetRepository extends JpaRepository<FlashcardSet, UUID> {
}
