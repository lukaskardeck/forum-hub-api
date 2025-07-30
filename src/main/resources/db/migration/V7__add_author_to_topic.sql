-- V7__add_topic_author.sql

-- 1. Adiciona a coluna author_id (ainda sem NOT NULL e sem constraint)
ALTER TABLE topic
ADD COLUMN author_id BIGINT;

-- 2. Atualiza os tópicos existentes com o ID de um usuário válido
-- (ajuste conforme o ID existente na sua tabela app_user)
UPDATE topic SET author_id = 1;

-- 3. Modifica a coluna para ser NOT NULL
ALTER TABLE topic
MODIFY COLUMN author_id BIGINT NOT NULL;

-- 4. Adiciona a constraint de chave estrangeira
ALTER TABLE topic
ADD CONSTRAINT fk_topic_author
FOREIGN KEY (author_id) REFERENCES app_user(id);