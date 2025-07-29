package com.lukaskardeck.forum_hub.domain;

import com.lukaskardeck.forum_hub.dto.course.CreateCourseRequest;
import com.lukaskardeck.forum_hub.dto.course.UpdateCourseRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    // Relacionamento com tópicos - um curso pode ter vários tópicos
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Topic> topics = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime creationDate;

    @Column(name = "updated_at")
    private LocalDateTime lastUpdated;

    public Course(CreateCourseRequest createCourse) {
        this.name = createCourse.name();
        this.category = createCourse.category();
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

    public void update(UpdateCourseRequest data) {
        if (data.name() != null) this.name = data.name();
        if (data.category() != null) this.category = data.category();
    }
}
