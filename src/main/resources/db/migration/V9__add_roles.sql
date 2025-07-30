-- V9__add_roles.sql

-- Cria tabela de roles
CREATE TABLE role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Tabela intermediária entre usuário e roles
CREATE TABLE user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES app_user(id),
    FOREIGN KEY (role_id) REFERENCES role(id),
    PRIMARY KEY (user_id, role_id)
);

-- Insere as roles básicas
INSERT INTO role (name) VALUES ('ROLE_USER'), ('ROLE_MODERATOR'), ('ROLE_ADMIN');

-- Associa o usuário de id 1 à role de MODERATOR
INSERT INTO user_role (user_id, role_id)
VALUES (1, (SELECT id FROM role WHERE name = 'ROLE_MODERATOR'));

-- Associa o usuário de id 2 à role de USER
INSERT INTO user_role (user_id, role_id)
VALUES (2, (SELECT id FROM role WHERE name = 'ROLE_USER'));
