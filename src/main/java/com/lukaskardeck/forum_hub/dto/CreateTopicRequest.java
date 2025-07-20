package com.lukaskardeck.forum_hub.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTopicRequest(
        @NotBlank
        String title,

        @NotBlank
        String message,

        @NotBlank
        String course
) {
}
