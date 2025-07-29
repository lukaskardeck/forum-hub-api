package com.lukaskardeck.forum_hub.dto.course;

import com.lukaskardeck.forum_hub.domain.Course;

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
