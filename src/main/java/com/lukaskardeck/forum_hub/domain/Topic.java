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

    // Muitos tópicos podem pertencer a um único curso
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    private String status;

    public Topic(CreateTopicRequest createTopic, Course course) {
        this.title = createTopic.title();
        this.message = createTopic.message();
        this.course = course;
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

    public void update(UpdateTopicRequest data, Course course) {
        if (data.title() != null) {
            this.title = data.title();
        }

        if (data.message() != null) {
            this.message = data.message();
        }

        if (course != null) {
            this.course = course;
        }
    }
}
