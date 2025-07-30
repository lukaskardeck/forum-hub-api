package com.lukaskardeck.forum_hub.dto.answer;

import com.lukaskardeck.forum_hub.domain.Answer;

import java.time.LocalDateTime;

public record AnswerDetailsResponse(
        Long id,
        String message,
        LocalDateTime creationDate,
        LocalDateTime lastUpdated,
        boolean isSolution,
        Long topicId
) {
    public AnswerDetailsResponse(Answer answer) {
        this(
                answer.getId(),
                answer.getMessage(),
                answer.getCreationDate(),
                answer.getLastUpdated(),
                answer.isSolution(),
                answer.getTopic().getId()
        );
    }
}
