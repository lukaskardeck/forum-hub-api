package com.lukaskardeck.forum_hub.controller;

import com.lukaskardeck.forum_hub.dto.course.CourseDetailsResponse;
import com.lukaskardeck.forum_hub.dto.course.CreateCourseRequest;
import com.lukaskardeck.forum_hub.dto.course.UpdateCourseRequest;
import com.lukaskardeck.forum_hub.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @Transactional
    public ResponseEntity<CourseDetailsResponse> create(
            @RequestBody @Valid
            CreateCourseRequest courseRequest,
            UriComponentsBuilder uriBuilder
    ) {

        var courseDetails = courseService.createCourse(courseRequest);

        // Criação da uri
        var uri = uriBuilder.path("/course/{id}").buildAndExpand(courseDetails.id()).toUri();

        return ResponseEntity.created(uri).body(courseDetails);
    }


    @GetMapping
    public ResponseEntity<Page<CourseDetailsResponse>> show(
            @PageableDefault(size = 10, sort = "creationDate", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        var courses = courseService.showCourses(pageable);

        return ResponseEntity.ok(courses);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CourseDetailsResponse> detail(@PathVariable Long id) {
        var course = courseService.detailsCourse(id);
        return ResponseEntity.ok(course);
    }


    @PutMapping
    @Transactional
    public ResponseEntity<CourseDetailsResponse> update(@RequestBody @Valid UpdateCourseRequest dataUpdate) {
        var course = courseService.updateCourse(dataUpdate);
        return ResponseEntity.ok(course);
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<CourseDetailsResponse> delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
