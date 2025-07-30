package com.lukaskardeck.forum_hub.dto.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateAnswerRequest(
        @NotNull Long id,
        @NotBlank String message
) {
}
