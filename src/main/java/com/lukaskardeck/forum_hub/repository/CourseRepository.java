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

    // Verifica se já existe curso com mesmo nome
    boolean existsByNameIgnoreCase(String name);

    // Busca curso com seus tópicos (fetch join para evitar N+1)
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.topics WHERE c.id = :id")
    Optional<Course> findByIdWithTopics(@Param("id") Long id);
}
