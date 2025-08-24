package com.lukaskardeck.forum_hub.service;

import com.lukaskardeck.forum_hub.domain.Course;
import com.lukaskardeck.forum_hub.domain.Topic;
import com.lukaskardeck.forum_hub.domain.User;
import com.lukaskardeck.forum_hub.dto.topic.UpdateTopicRequest;
import com.lukaskardeck.forum_hub.dto.topic.CreateTopicRequest;
import com.lukaskardeck.forum_hub.dto.topic.TopicDetailsResponse;
import com.lukaskardeck.forum_hub.repository.CourseRepository;
import com.lukaskardeck.forum_hub.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AuthService authService;


    public TopicDetailsResponse create(CreateTopicRequest topicRequest) {
        var title = topicRequest.title();
        var message = topicRequest.message();

        // Verifica se já existe um tópico no banco que seja igual ao que está sendo enviado, para evitar duplicatas
        var isDuplicated = topicRepository.existsByTitleAndMessage(title, message);
        if (isDuplicated) {
            throw new IllegalArgumentException("Já existe um tópico registrado com esse título e mensagem!");
        }

        var course = courseRepository.findById(topicRequest.courseId())
                .orElseThrow(() -> new EntityNotFoundException("Curso com ID " + topicRequest.courseId() + " não encontrado"));


        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var userAuthor = (User) authService.loadUserByUsername(currentUsername);

        var newTopic = new Topic(topicRequest, course, userAuthor);
        topicRepository.save(newTopic);

        return new TopicDetailsResponse(newTopic);
    }


    public Page<TopicDetailsResponse> list(Long courseId, String authorLogin, Pageable pageable) {
        Page<Topic> topics;

        if (courseId != null && authorLogin != null) {
            topics = topicRepository.findByCourseIdAndAuthor(courseId, authorLogin, pageable);
        } else if (courseId != null) {
            topics = topicRepository.findByCourseId(courseId, pageable);
        } else if (authorLogin != null) {
            topics = topicRepository.findByAuthor(authorLogin, pageable);
        } else {
            topics = topicRepository.findAll(pageable);
        }

        return topics.map(TopicDetailsResponse::new);
    }




    public TopicDetailsResponse detail(Long id) {
        var topic = topicRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Tópico com ID " + id + " não encontrado")
                );
        return new TopicDetailsResponse(topic);
    }


    public TopicDetailsResponse update(UpdateTopicRequest data) {
        var topic = topicRepository.findById(data.id())
                .orElseThrow(
                        () -> new EntityNotFoundException("Tópico com ID " + data.id() + " não encontrado")
                );

        var currentUser = authService.getAuthenticatedUser();
        if (!topic.getAuthor().equals(currentUser)) {
            throw new AccessDeniedException("Você não tem permissão para alterar este tópico.");
        }

        Course course = null;
        if (data.courseId() != null) {
            course = courseRepository.findById(data.courseId())
                    .orElseThrow(() -> new EntityNotFoundException("Curso com ID " + data.courseId() + " não encontrado"));
        }

        topic.update(data, course);
        topicRepository.flush(); // força o JPA a sincronizar as alterações com o banco

        return new TopicDetailsResponse(topic);
    }


    public void delete(Long id) {
        var topic = topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico com ID " + id + " não encontrado"));

        var currentUser = authService.getAuthenticatedUser();
        if (!topic.getAuthor().equals(currentUser)) {
            throw new AccessDeniedException("Você não tem permissão para deletar este tópico.");
        }

        topicRepository.delete(topic);
    }
}
