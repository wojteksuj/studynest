package org.example.studynest.dto.response;

import java.util.UUID;

public class FlashcardSetDTO {
    private UUID id;
    private String title;
    private String description;
    private String topic;

    public FlashcardSetDTO(UUID id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public FlashcardSetDTO(UUID id, String title, String description, String topic) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.topic = topic;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
