package org.example.studynest.repository;

import org.example.studynest.dto.response.FlashcardSetDTO;
import org.example.studynest.entity.FlashcardSet;
import org.example.studynest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
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
