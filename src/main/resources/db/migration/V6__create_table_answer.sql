-- Migration V6: Criando a tabela de respostas

CREATE TABLE answer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    message TEXT NOT NULL,
    creation_date DATETIME NOT NULL,
    last_updated DATETIME NOT NULL,
    is_solution BOOLEAN NOT NULL DEFAULT FALSE,
    topic_id BIGINT NOT NULL,

    CONSTRAINT fk_answer_topic
        FOREIGN KEY (topic_id)
        REFERENCES topic(id)
        ON DELETE CASCADE
);
