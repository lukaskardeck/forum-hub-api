package com.lukaskardeck.forum_hub.domain.topic;

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
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String course;

    @Column(name = "creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();

    private String status;

    public Topic(CreateTopicRequest createTopic) {
        this.title = createTopic.title();
        this.message = createTopic.message();
        this.course = createTopic.course();
    }
}
