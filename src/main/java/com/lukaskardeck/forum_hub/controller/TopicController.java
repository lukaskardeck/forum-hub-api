package com.lukaskardeck.forum_hub.controller;

import com.lukaskardeck.forum_hub.domain.topic.CreateTopicRequest;
import com.lukaskardeck.forum_hub.domain.topic.TopicService;
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
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    /*
    *
    *  CRIA UM NOVO TÓPICO
    *
    * */
    @PostMapping
    @Transactional
    public ResponseEntity create(
            @RequestBody @Valid CreateTopicRequest topicRequest,    // corpo da requisição para criar um tópico
            UriComponentsBuilder uriBuilder                         // criador da uri para adicionar no header da response
    ) {

        // Cria um "Topico" no banco de dados, e retorna os detalhes do novo topico criado
        var topicDetails = topicService.create(topicRequest);

        // Criação da uri
        var uri = uriBuilder.path("/topic/{id}").buildAndExpand(topicDetails.id()).toUri();

        // Retorna o código HTTP 201, com os detalhes do topico no corpo da resposta, bem como a uri no header
        return ResponseEntity.created(uri).body(topicDetails);
    }
}
