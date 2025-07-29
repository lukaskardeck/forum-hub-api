package com.lukaskardeck.forum_hub.controller;

import com.lukaskardeck.forum_hub.dto.CourseDTO;
import com.lukaskardeck.forum_hub.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @Transactional
    public ResponseEntity create(
            @RequestBody @Valid
            CourseDTO.CreateCourseRequest courseRequest,
            UriComponentsBuilder uriBuilder
    ) {

        var courseDetails = courseService.createCourse(courseRequest);

        // Criação da uri
        var uri = uriBuilder.path("/course/{id}").buildAndExpand(courseDetails.id()).toUri();

        return ResponseEntity.created(uri).body(courseDetails);
    }
}
