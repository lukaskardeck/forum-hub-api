package com.lukaskardeck.forum_hub.controller;

import com.lukaskardeck.forum_hub.dto.topic.CreateTopicRequest;
import com.lukaskardeck.forum_hub.dto.topic.TopicDetailsResponse;
import com.lukaskardeck.forum_hub.service.TopicService;
import com.lukaskardeck.forum_hub.dto.topic.UpdateTopicRequest;
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
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    /*
    *  CRIA UM NOVO TÓPICO
    * */
    @PostMapping
    @Transactional
    public ResponseEntity create(
            @RequestBody @Valid
            CreateTopicRequest topicRequest,    // corpo da requisição para criar um tópico

            UriComponentsBuilder uriBuilder     // criador da uri para adicionar no header da response
    ) {

        // Cria um "Topico" no banco de dados, e retorna os detalhes do novo topico criado
        var topicDetails = topicService.create(topicRequest);

        // Criação da uri
        var uri = uriBuilder.path("/topic/{id}").buildAndExpand(topicDetails.id()).toUri();

        // Retorna o código HTTP 201, com os detalhes do topico no corpo da resposta, bem como a uri no header
        return ResponseEntity.created(uri).body(topicDetails);
    }


    /*
     *  LISTA OS TÓPICOS COM O RECURSO DE PAGINAÇÃO
     * */
    @GetMapping
    public ResponseEntity<Page<TopicDetailsResponse>> list(
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String authorLogin,
            @PageableDefault(size = 10, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        var response = topicService.list(courseId, authorLogin, pageable);
        return ResponseEntity.ok(response);
    }


    /*
     *  DETALHANDO UM TÓPICO ESPECÍFICO, PELO ID
     * */
    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable Long id) {
        var topic = topicService.detail(id);
        return ResponseEntity.ok(topic);
    }


    /*
     *  ATUALIZA UM TÓPICO
     * */
    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody UpdateTopicRequest request) {
        var topic = topicService.update(request);
        return ResponseEntity.ok(topic);
    }


    /*
     *  DELETAR UM TÓPICO
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        topicService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
