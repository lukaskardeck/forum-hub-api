package com.lukaskardeck.forum_hub.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateTopicRequest(
        @NotNull
        Long id,

        String title,
        String message,
        String course
) {
}
