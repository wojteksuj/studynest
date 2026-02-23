package org.example.studynest.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class CreateFlashcardSetDTO {

    @NotBlank(message = "Title can not be empty.")
    private String title;
    private String description;
    @NotBlank
    private UUID topicId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getTopicId() {
        return topicId;
    }

    public void setTopicId(UUID topicId) {
        this.topicId = topicId;
    }
}
