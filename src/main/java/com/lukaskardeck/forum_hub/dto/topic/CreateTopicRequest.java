package com.lukaskardeck.forum_hub.dto.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTopicRequest(
        @NotBlank
        String title,

        @NotBlank
        String message,

        @NotNull
        Long courseId
) {
}
