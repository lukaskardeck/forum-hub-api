-- Migration V4: Criação da tabela de cursos

CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Constraint para garantir nome único (case insensitive)
    UNIQUE KEY uk_course_name (name)
);

-- Inserir alguns cursos de exemplo
INSERT INTO courses (name, category) VALUES
('Spring Boot Fundamentals', 'Backend'),
('React Development', 'Frontend'),
('Java Advanced', 'Backend'),
('JavaScript Essentials', 'Frontend'),
('Database Design', 'Database'),
('DevOps with Docker', 'DevOps');

-- Comentários nas colunas para documentação
ALTER TABLE courses
MODIFY COLUMN name VARCHAR(255) NOT NULL COMMENT 'Nome do curso - deve ser único',
MODIFY COLUMN category VARCHAR(255) NOT NULL COMMENT 'Categoria do curso (ex: Backend, Frontend, Database)';