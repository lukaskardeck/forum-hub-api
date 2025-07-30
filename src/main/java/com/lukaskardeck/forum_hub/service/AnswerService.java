package com.lukaskardeck.forum_hub.service;

import com.lukaskardeck.forum_hub.domain.Answer;
import com.lukaskardeck.forum_hub.domain.Topic;
import com.lukaskardeck.forum_hub.dto.answer.AnswerDetailsResponse;
import com.lukaskardeck.forum_hub.dto.answer.CreateAnswerRequest;
import com.lukaskardeck.forum_hub.dto.answer.UpdateAnswerRequest;
import com.lukaskardeck.forum_hub.repository.AnswerRepository;
import com.lukaskardeck.forum_hub.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TopicRepository topicRepository;

    public AnswerDetailsResponse createAnswer(CreateAnswerRequest answerRequest) {
        var topicId = answerRequest.topicId();
        var topic = topicRepository.findById(topicId).orElseThrow(
                () -> new EntityNotFoundException("Tópico com ID " + topicId + " não encontrado")
        );

        var newAnswer = new Answer(answerRequest, topic);
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

        answer.update(updateRequest);
        answerRepository.flush();

        return new AnswerDetailsResponse(answer);
    }


    public void deleteAnswer(Long id) {
        var answer = answerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Resposta com ID " + id + " não encontrada")
        );

        answerRepository.delete(answer);
    }
}

