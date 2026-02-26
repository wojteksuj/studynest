package org.example.studynest.repository;

import jakarta.transaction.Transactional;
import org.example.studynest.dto.response.FlashcardSetDTO;
import org.example.studynest.entity.FlashcardSet;
import org.example.studynest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlashcardSetRepository extends JpaRepository<FlashcardSet, UUID> {

    @Query("""
            SELECT new org.example.studynest.dto.response.FlashcardSetDTO(
                                          fs.id,
                                          fs.title,
                                          fs.description
                                      )
                                      FROM FlashcardSet fs
                                      JOIN fs.user u
                                      WHERE u.id = :id
            """)
    List<FlashcardSetDTO> findAllById(@Param("id") UUID id);


    @Query("""
            SELECT new org.example.studynest.dto.response.FlashcardSetDTO(
                                          fs.id,
                                          fs.title,
                                          fs.description
                                      )
                                      FROM FlashcardSet fs
                                      WHERE fs.id = :id and fs.user.id = :userId
            """)
    Optional<FlashcardSetDTO> findFlashcardSetById(@Param("id") UUID id, @Param("userId") UUID userId);

    Optional<FlashcardSet> findFlashcardSetByIdAndUserId(UUID id, UUID userId);
}

