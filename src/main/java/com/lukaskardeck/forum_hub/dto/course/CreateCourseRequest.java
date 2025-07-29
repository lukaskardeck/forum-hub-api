package com.lukaskardeck.forum_hub.dto.course;

import jakarta.validation.constraints.NotBlank;

public record CreateCourseRequest(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Categoria é obrigatória")
        String category
) {}
