package org.example.studynest.repository;

import org.example.studynest.dto.response.FlashcardSetDTO;
import org.example.studynest.entity.FlashcardSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FlashcardSetRepository extends JpaRepository<FlashcardSet, UUID> {

    @Query("""
            SELECT new org.example.studynest.dto.response.FlashcardSetDTO(
                                          fs.id,
                                          fs.title
                                      )
                                      FROM FlashcardSet fs
                                      JOIN fs.user u
                                      WHERE u.id = :id
            """)
    List<FlashcardSetDTO> findAllById(@Param("id") UUID id);


}
