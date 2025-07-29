package com.lukaskardeck.forum_hub.service;

import com.lukaskardeck.forum_hub.domain.Course;
import com.lukaskardeck.forum_hub.dto.course.CourseDetailsResponse;
import com.lukaskardeck.forum_hub.dto.course.CreateCourseRequest;
import com.lukaskardeck.forum_hub.dto.course.UpdateCourseRequest;
import com.lukaskardeck.forum_hub.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // Criar novo curso
    @Transactional
    public CourseDetailsResponse createCourse(CreateCourseRequest courseRequest) {
        // Verificar se já existe curso com mesmo nome
        if (courseRepository.existsByNameIgnoreCase(courseRequest.name())) {
            throw new IllegalArgumentException("Curso com este nome já existe");
        }

        Course newCourse = new Course(courseRequest);
        courseRepository.save(newCourse);

        return new CourseDetailsResponse(newCourse);
    }


    // Lista todos os cursos cadastrados
    public Page<CourseDetailsResponse> showCourses(Pageable pageable) {
        var courses = courseRepository.findAll(pageable);
        return courses.map(CourseDetailsResponse::new);
    }


    // Detalha um curso específico
    @Transactional(readOnly = true)
    public CourseDetailsResponse detailsCourse(Long id) {
        var course = courseRepository.findByIdWithTopics(id).orElseThrow(
                () -> new EntityNotFoundException("Curso com ID " + id + " não encontrado")
        );
        return new CourseDetailsResponse(course);
    }


    // Atualiza um curso
    public CourseDetailsResponse updateCourse(UpdateCourseRequest dataUpdate) {
        var id = dataUpdate.id();
        var course = courseRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Curso com ID " + id + " não encontrado")
        );

        course.update(dataUpdate);
        courseRepository.flush(); // força o JPA a sincronizar as alterações com o banco

        return new CourseDetailsResponse(course);
    }


    // Deleta um curso
    public void deleteCourse(Long id) {
        var course = courseRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Curso com ID " + id + " não encontrado")
        );

        courseRepository.delete(course);
    }

}
