package com.lukaskardeck.forum_hub.dto.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAnswerRequest(
        @NotBlank String message,
        @NotNull Long topicId
) {
}