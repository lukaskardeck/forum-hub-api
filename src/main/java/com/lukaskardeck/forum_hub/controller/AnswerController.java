package com.lukaskardeck.forum_hub.controller;

import com.lukaskardeck.forum_hub.dto.answer.AnswerDetailsResponse;
import com.lukaskardeck.forum_hub.dto.answer.CreateAnswerRequest;
import com.lukaskardeck.forum_hub.dto.answer.UpdateAnswerRequest;
import com.lukaskardeck.forum_hub.service.AnswerService;
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
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping
    @Transactional
    public ResponseEntity<AnswerDetailsResponse> create(
            @RequestBody @Valid CreateAnswerRequest answerRequest,
            UriComponentsBuilder uriBuilder
    ) {

        var answerDetails = answerService.createAnswer(answerRequest);

        // Criação da uri
        var uri = uriBuilder.path("/answer/{id}").buildAndExpand(answerDetails.id()).toUri();

        return ResponseEntity.created(uri).body(answerDetails);
    }


    @GetMapping
    public ResponseEntity<Page<AnswerDetailsResponse>> show(
            @PageableDefault(size = 10, sort = "creationDate", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
      var answers = answerService.showAnswers(pageable);
      return ResponseEntity.ok(answers);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AnswerDetailsResponse> details(@PathVariable Long id) {
        var answer = answerService.detailsAnswer(id);
        return ResponseEntity.ok(answer);
    }


    @PutMapping
    @Transactional
    public ResponseEntity<AnswerDetailsResponse> update(@RequestBody @Valid UpdateAnswerRequest updateRequest) {
        var answerToUpdate = answerService.updateAnswer(updateRequest);
        return ResponseEntity.ok(answerToUpdate);
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }
}
