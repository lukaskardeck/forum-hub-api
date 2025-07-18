package com.lukaskardeck.forum_hub.domain.topic;

import java.time.LocalDateTime;

public record TopicDetailsResponse(
        Long id,
        String title,
        String message,
        String course,
        LocalDateTime creationDate,
        LocalDateTime lastUpdate
) {

    public TopicDetailsResponse(Topic topic) {
        this(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCourse(),
                topic.getCreationDate(),
                topic.getLastUpdated()
        );
    }
}
