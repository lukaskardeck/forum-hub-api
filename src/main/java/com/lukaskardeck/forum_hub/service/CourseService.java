package com.lukaskardeck.forum_hub.service;

import com.lukaskardeck.forum_hub.domain.Course;
import com.lukaskardeck.forum_hub.dto.CourseDTO;
import com.lukaskardeck.forum_hub.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // Criar novo curso
    @Transactional
    public CourseDTO.CourseDetailsResponse createCourse(CourseDTO.CreateCourseRequest courseRequest) {
        // Verificar se já existe curso com mesmo nome
        if (courseRepository.existsByNameIgnoreCase(courseRequest.name())) {
            throw new IllegalArgumentException("Curso com este nome já existe");
        }

        Course newCourse = new Course(courseRequest);
        courseRepository.save(newCourse);

        return new CourseDTO.CourseDetailsResponse(newCourse);
    }



}
