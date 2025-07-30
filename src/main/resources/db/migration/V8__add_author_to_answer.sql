-- V8__add_author_to_answer.sql

-- 1. Adiciona a coluna author_id
ALTER TABLE answer
ADD COLUMN author_id BIGINT;

-- 2. Atualiza respostas existentes para o usuário de Id = 2
UPDATE answer SET author_id = 2;

-- 3. Define a coluna como NOT NULL
ALTER TABLE answer
MODIFY COLUMN author_id BIGINT NOT NULL;

-- 4. Adiciona a constraint de chave estrangeira
ALTER TABLE answer
ADD CONSTRAINT fk_answer_author
FOREIGN KEY (author_id) REFERENCES app_user(id);
