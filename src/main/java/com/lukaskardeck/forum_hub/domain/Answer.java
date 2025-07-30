package com.lukaskardeck.forum_hub.domain;

import com.lukaskardeck.forum_hub.dto.answer.CreateAnswerRequest;
import com.lukaskardeck.forum_hub.dto.answer.UpdateAnswerRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false, name = "creation_date")
    private LocalDateTime creationDate;

    @Column(nullable = false, name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column(nullable = false)
    private boolean isSolution = false;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    public Answer(CreateAnswerRequest answerRequest, Topic topic) {
        this.message = answerRequest.message();
        this.topic = topic;
    }

    public void update(UpdateAnswerRequest updateRequest) {
        if (updateRequest.message() != null) {
            this.message = updateRequest.message();
        }
    }

    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }
}
