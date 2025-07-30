package com.lukaskardeck.forum_hub.dto.answer;

import com.lukaskardeck.forum_hub.domain.Answer;

import java.time.LocalDateTime;

public record AnswerResponse(
        Long id,
        String message,
        Long authorId,
        LocalDateTime lastUpdated,
        boolean isSolution
) {
    public AnswerResponse(Answer answer) {
        this(
                answer.getId(),
                answer.getMessage(),
                answer.getAuthor().getId(),
                answer.getLastUpdated(),
                answer.isSolution()
        );
    }
}
