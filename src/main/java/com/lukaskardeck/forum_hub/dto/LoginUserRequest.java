package com.lukaskardeck.forum_hub.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginUserRequest(
        @NotBlank
        String login,

        @NotBlank
        String password
) {
}
