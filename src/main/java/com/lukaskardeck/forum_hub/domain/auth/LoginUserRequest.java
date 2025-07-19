package com.lukaskardeck.forum_hub.domain.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginUserRequest(
        @NotBlank
        String login,

        @NotBlank
        String password
) {
}
