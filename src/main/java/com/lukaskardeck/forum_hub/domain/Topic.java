package com.lukaskardeck.forum_hub.domain;

import com.lukaskardeck.forum_hub.dto.topic.CreateTopicRequest;
import com.lukaskardeck.forum_hub.dto.topic.UpdateTopicRequest;
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
    private LocalDateTime creationDate;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    private String status;

    public Topic(CreateTopicRequest createTopic) {
        this.title = createTopic.title();
        this.message = createTopic.message();
        this.course = createTopic.course();
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

    public void update(UpdateTopicRequest data) {
        if (data.title() != null) {
            this.title = data.title();
        }

        if (data.message() != null) {
            this.message = data.message();
        }

        if (data.course() != null) {
            this.course = data.course();
        }
    }
}
