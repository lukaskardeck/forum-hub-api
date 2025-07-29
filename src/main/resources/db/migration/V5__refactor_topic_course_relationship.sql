-- Migration V5: Criando uma relação entre as entidades Topic e Course

-- 1. Apagar os tópicos existentes
DELETE FROM topic;

-- 2. Renomear a tabela de cursos
RENAME TABLE courses TO course;

-- 3. Remover a coluna antiga 'course' (string)
ALTER TABLE topic DROP COLUMN course;

-- 4. Adicionar coluna 'course_id' como FK (não pode ser nulo)
ALTER TABLE topic
ADD COLUMN course_id BIGINT NOT NULL;

-- 5. Adicionar a constraint de chave estrangeira
ALTER TABLE topic
ADD CONSTRAINT fk_topic_course
FOREIGN KEY (course_id) REFERENCES course(id);

-- Inserindo tópicos relacionados aos cursos
INSERT INTO topic (title, message, course_id, creation_date, status, last_updated)
VALUES
('Problema com @Autowired', 'Não consigo injetar dependência no controller.', 1, NOW(), 'ABERTO', NOW()),
('Como usar props no React?', 'Estou tentando passar dados entre componentes.', 2, NOW(), 'ABERTO', NOW()),
('Dúvida sobre Generics', 'Como usar wildcards corretamente em Java?', 3, NOW(), 'ABERTO', NOW()),
('Arrow functions vs funções normais', 'Qual a diferença prática?', 4, NOW(), 'ABERTO', NOW()),
('Normalização de banco', 'Quando usar 1FN, 2FN, 3FN?', 5, NOW(), 'ABERTO', NOW()),
('Volumes em Docker', 'Como persistir dados em containers?', 6, NOW(), 'ABERTO', NOW());
