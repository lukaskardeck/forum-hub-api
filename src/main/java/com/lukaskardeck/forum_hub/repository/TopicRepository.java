package com.lukaskardeck.forum_hub.repository;

import com.lukaskardeck.forum_hub.domain.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Boolean existsByTitleAndMessage(String title, String message);

    @EntityGraph(attributePaths = "answers")
    Page<Topic> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "answers")
    @Query("SELECT t FROM Topic t WHERE t.course.name = :courseName")
    Page<Topic> findByCourse(@Param("courseName") String courseName, Pageable pageable);

    @EntityGraph(attributePaths = "answers")
    Page<Topic> findByCourseId(Long courseId, Pageable pageable);

    @EntityGraph(attributePaths = "answers")
    @Query("SELECT t FROM Topic t WHERE t.author.login = :login")
    Page<Topic> findByAuthor(@Param("login") String login, Pageable pageable);

    @EntityGraph(attributePaths = "answers")
    @Query("SELECT t FROM Topic t WHERE t.course.name = :courseName AND t.author.login = :login")
    Page<Topic> findByCourseAndAuthor(
            @Param("courseName") String courseName,
            @Param("login") String login,
            Pageable pageable
    );

    @EntityGraph(attributePaths = "answers")
    Page<Topic> findByCourseIdAndAuthor(Long courseId, String authorLogin, Pageable pageable);

}

