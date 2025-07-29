package com.lukaskardeck.forum_hub.repository;

import com.lukaskardeck.forum_hub.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Busca por nome (case insensitive)
    List<Course> findByNameContainingIgnoreCase(String name);

    // Busca por categoria
    List<Course> findByCategory(String category);

    // Busca por categoria (case insensitive)
    List<Course> findByCategoryContainingIgnoreCase(String category);

    // Verifica se já existe curso com mesmo nome
    boolean existsByNameIgnoreCase(String name);

    // Busca curso com seus tópicos (fetch join para evitar N+1)
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.topics WHERE c.id = :id")
    Optional<Course> findByIdWithTopics(@Param("id") Long id);

    // Lista cursos com paginação
    Page<Course> findAll(Pageable pageable);

    // Lista cursos por categoria com paginação
    Page<Course> findByCategory(String category, Pageable pageable);
}
