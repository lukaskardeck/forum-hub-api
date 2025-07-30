package com.lukaskardeck.forum_hub.service;

import com.lukaskardeck.forum_hub.domain.Answer;
import com.lukaskardeck.forum_hub.domain.User;
import com.lukaskardeck.forum_hub.dto.answer.AnswerDetailsResponse;
import com.lukaskardeck.forum_hub.dto.answer.CreateAnswerRequest;
import com.lukaskardeck.forum_hub.dto.answer.UpdateAnswerRequest;
import com.lukaskardeck.forum_hub.repository.AnswerRepository;
import com.lukaskardeck.forum_hub.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private AuthService authService;

    public AnswerDetailsResponse createAnswer(CreateAnswerRequest answerRequest) {
        var topicId = answerRequest.topicId();
        var topic = topicRepository.findById(topicId).orElseThrow(
                () -> new EntityNotFoundException("Tópico com ID " + topicId + " não encontrado")
        );

        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var userAuthor = (User) authService.loadUserByUsername(currentUsername);

        var newAnswer = new Answer(answerRequest, topic, userAuthor);
        answerRepository.save(newAnswer);

        return new AnswerDetailsResponse(newAnswer);
    }


    public Page<AnswerDetailsResponse> showAnswers(Pageable pageable) {
        var answers = answerRepository.findAll(pageable);
        return answers.map(AnswerDetailsResponse::new);
    }


    public AnswerDetailsResponse detailsAnswer(Long id) {
        var answer = answerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Resposta com ID " + id + " não encontrada")
        );

        return new AnswerDetailsResponse(answer);
    }


    public AnswerDetailsResponse updateAnswer(UpdateAnswerRequest updateRequest) {
        var idAnswer = updateRequest.id();
        var answer = answerRepository.findById(idAnswer).orElseThrow(
                () -> new EntityNotFoundException("Resposta com ID " + idAnswer + " não encontrada")
        );

        validateAuthor(answer);
        answer.update(updateRequest);
        answerRepository.flush();

        return new AnswerDetailsResponse(answer);
    }


    public void deleteAnswer(Long id) {
        var answer = answerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Resposta com ID " + id + " não encontrada")
        );

        validateAuthor(answer);
        answerRepository.delete(answer);
    }


    private void validateAuthor(Answer answer) {
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var answerAuthorUsername = answer.getAuthor().getUsername();

        if (!answerAuthorUsername.equals(currentUsername)) {
            throw new AccessDeniedException("Você não tem permissão para modificar esta resposta.");
        }
    }
}

