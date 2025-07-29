package com.lukaskardeck.forum_hub.dto;

import com.lukaskardeck.forum_hub.domain.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CourseDTO {

    // DTO para criação de curso
    public record CreateCourseRequest(
            @NotBlank(message = "Nome é obrigatório")
            String name,

            @NotBlank(message = "Categoria é obrigatória")
            String category
    ) {}


    // DTO para atualização de curso
    public record UpdateCourseRequest(
            @NotNull
            Long id,

            String name,
            String category
    ) {
    }


    // DTO para resposta básica
    public record CourseDetailsResponse(
            Long id,
            String name,
            String category,
            Integer totalTopics
    ) {
        public CourseDetailsResponse(Course course) {
            this(
                    course.getId(),
                    course.getName(),
                    course.getCategory(),
                    course.getTopics().size()
            );
        }
    }
}
