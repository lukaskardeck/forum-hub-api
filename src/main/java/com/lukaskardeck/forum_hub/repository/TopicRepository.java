package com.lukaskardeck.forum_hub.repository;

import com.lukaskardeck.forum_hub.domain.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Boolean existsByTitleAndMessage(String title, String message);

    // Busca por nome do curso (usando relacionamento course.name)
    Page<Topic> findByCourse_Name(String courseName, Pageable pageable);

    // Busca por ano da data de criação
    @Query("SELECT t FROM Topic t WHERE YEAR(t.creationDate) = :year")
    Page<Topic> findByYear(@Param("year") Integer year, Pageable pageable);

    // Busca por nome do curso + ano
    @Query("SELECT t FROM Topic t WHERE t.course.name = :courseName AND YEAR(t.creationDate) = :year")
    Page<Topic> findByCourseNameAndYear(@Param("courseName") String courseName, @Param("year") Integer year, Pageable pageable);
}

