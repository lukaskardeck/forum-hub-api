package com.lukaskardeck.forum_hub.dto.topic;

import com.lukaskardeck.forum_hub.domain.Topic;
import com.lukaskardeck.forum_hub.dto.answer.AnswerResponse;

import java.time.LocalDateTime;
import java.util.List;

public record TopicDetailsResponse(
        Long id,
        String title,
        String message,
        String course,
        String author,
        LocalDateTime creationDate,
        LocalDateTime lastUpdate,
        Integer totalAnswers,
        List<AnswerResponse> answers
) {
    public TopicDetailsResponse(Topic topic) {
        this(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCourse().getName(),
                topic.getAuthor().getUsername(),
                topic.getCreationDate(),
                topic.getLastUpdated(),
                topic.getAnswers().size(),
                topic.getAnswers().stream().map(AnswerResponse::new).toList()
        );
    }
}

