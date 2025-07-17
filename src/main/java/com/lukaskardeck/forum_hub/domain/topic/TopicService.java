package com.lukaskardeck.forum_hub.domain.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public TopicDetailsResponse create(CreateTopicRequest topicRequest) {
        var title = topicRequest.title();
        var message = topicRequest.message();

        // Verifica se já existe um tópico no banco que seja igual ao que está sendo enviado, para evitar duplicatas
        var isDuplicated = topicRepository.existsByTitleAndMessage(title, message);
        if (isDuplicated) {
            throw new IllegalArgumentException("Já existe um tópico registrado com esse título e mensagem!");
        }

        var newTopic = new Topic(topicRequest);
        topicRepository.save(newTopic);

        return new TopicDetailsResponse(newTopic);
    }
}
