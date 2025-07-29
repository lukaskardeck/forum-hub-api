package com.lukaskardeck.forum_hub.dto.course;

import jakarta.validation.constraints.NotNull;

public record UpdateCourseRequest(
        @NotNull
        Long id,

        String name,
        String category
) {
}