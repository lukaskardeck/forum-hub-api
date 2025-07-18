package com.lukaskardeck.forum_hub.domain.topic;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    public Page<TopicDetailsResponse> list(String course, Integer year, Pageable pageable) {
        Page<Topic> topics;
        if (course != null && year != null) {
            topics = topicRepository.findByCourseAndYear(course, year, pageable);
        } else if (course != null) {
            topics = topicRepository.findByCourse(course, pageable);
        } else if (year != null) {
            topics = topicRepository.findByYear(year, pageable);
        } else {
            topics = topicRepository.findAll(pageable);
        }

        // Transforma cada tópico da lista (Page) em um TopicDetailsResponse, e retorna essa lista
        return topics.map(TopicDetailsResponse::new);
    }


    public TopicDetailsResponse detail(Long id) {
        var topic = topicRepository.getReferenceById(id);
        return new TopicDetailsResponse(topic);
    }


    public TopicDetailsResponse update(UpdateTopicRequest data) {
        var topic = topicRepository.getReferenceById(data.id());
        topic.update(data);
        topicRepository.flush(); // força o JPA a sincronizar as alterações com o banco

        return new TopicDetailsResponse(topic);
    }


    public void delete(Long id) {
        topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não encontrado: " + id));
        topicRepository.deleteById(id);
    }
}
