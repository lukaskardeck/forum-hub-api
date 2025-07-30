package com.lukaskardeck.forum_hub.repository;

import com.lukaskardeck.forum_hub.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
