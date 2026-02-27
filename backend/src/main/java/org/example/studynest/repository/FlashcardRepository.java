package org.example.studynest.repository;

import org.example.studynest.dto.response.FlashcardDTO;
import org.example.studynest.entity.Flashcard;
import java.util.List;

import org.example.studynest.entity.FlashcardSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, UUID> {

    @Query("""
            SELECT new org.example.studynest.dto.response.FlashcardDTO(
                                              fc.id,
                                              fc.prompt,
                                              fc.answer,
                                              fc.orderIndex
                                          )
            FROM Flashcard fc
            JOIN fc.flashcardSet fs
            WHERE fs.id = :setId AND fs.user.id = :userId
            ORDER BY fc.orderIndex
            """)
    List<FlashcardDTO> findFlashcardsBySetId(@Param("setId") UUID setId, @Param("userId") UUID userId);

    int countAllByFlashcardSet(FlashcardSet flashcardSet);
    Optional<Flashcard> findByIdAndFlashcardSetUserId(UUID flashcardId, UUID userId);
}
