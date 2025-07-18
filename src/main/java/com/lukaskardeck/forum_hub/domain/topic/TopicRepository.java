package com.lukaskardeck.forum_hub.domain.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Boolean existsByTitleAndMessage(String title, String message);

    Page<Topic> findByCourse(String course, Pageable pageable);

    @Query("SELECT t FROM Topic t WHERE YEAR(t.creationDate) = :year")
    Page<Topic> findByYear(@Param("year") Integer year, Pageable pageable);

    @Query("SELECT t FROM Topic t WHERE t.course = :course AND YEAR(t.creationDate) = :year")
    Page<Topic> findByCourseAndYear(@Param("course") String course, @Param("year") Integer year, Pageable pageable);

}
