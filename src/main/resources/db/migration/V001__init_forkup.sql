CREATE SCHEMA IF NOT EXISTS forkup;

--- Criando as tabelas

CREATE TABLE IF NOT EXISTS forkup.tipo_usuario (
    id UUID NOT NULL PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS forkup.endereco (
    id UUID NOT NULL PRIMARY KEY,
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    complemento VARCHAR(255),
    cidade VARCHAR(255) NOT NULL,
    cep VARCHAR(9) NOT NULL
);

CREATE TABLE IF NOT EXISTS forkup.usuario (
    id UUID NOT NULL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario_id UUID NOT NULL REFERENCES forkup.tipo_usuario(id),
    endereco_id UUID REFERENCES forkup.endereco(id)
);

CREATE TABLE IF NOT EXISTS forkup.restaurante (
    id UUID NOT NULL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo_cozinha VARCHAR(100) NOT NULL,
    horario_funcionamento VARCHAR(100) NOT NULL,
    endereco_id UUID REFERENCES forkup.endereco(id),
    dono_id UUID NOT NULL REFERENCES forkup.usuario(id)
);

CREATE TABLE IF NOT EXISTS forkup.item_cardapio (
    id UUID NOT NULL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(500),
    preco DECIMAL(10, 2) NOT NULL,
    apenas_restaurante BOOLEAN NOT NULL DEFAULT FALSE,
    foto_url VARCHAR(255),
    restaurante_id UUID NOT NULL REFERENCES forkup.restaurante(id)
);

INSERT INTO forkup.tipo_usuario (id, descricao) VALUES
    ('2f8c9a0b-2d3e-4b6c-9a0b-2d3e4b6c9a0b', 'Dono de Restaurante'),
    ('1e7b8f9e-1c2d-4a5b-8f9e-1c2d4a5b8f9e', 'Cliente'),
    ('3f9d0b1c-3e4f-5c7d-8b1c-3e4f5c7d0b1c', 'Administrador'),
    ('09309e13-38ed-4250-8a2c-d4bdd5e80a9b', 'Tipo Usuário a ser Excluido'),
    ('731b81c2-e0c5-49e3-b8d3-e20be7786e99', 'Tipo Usuário a ser Alterado');

INSERT INTO forkup.endereco (id, logradouro, numero, complemento, cidade, cep) VALUES
    ('4a7f93ec-5940-4803-833b-540dd3fade4e', 'Rua Exemplo', '123', 'Apto 101', 'Cidade Exemplo', '12345-678'),
    ('fb592482-462b-4902-a0f7-0b658374d28d', 'Avenida Teste', '456', NULL, 'Cidade Teste', '23456-789'),
    ('59f598a2-8864-4738-b9b6-3b32c1c18ec9', 'Praça Central', '789', 'Sala 202', 'Cidade Central', '34567-890'),
    ('0e426136-f74a-4562-9818-2c46d25f0814', 'Travessa Nova', '321', NULL, 'Cidade Nova', '45678-901'),
    ('974bfcd3-3261-47a1-a526-e47e192d2b5c', 'Rua Exemplo', '123', 'Apto 101', 'Cidade Exemplo', '12345-678'),
    ('d907095c-6c25-403e-a223-cde862685360', 'Avenida Teste', '456', NULL, 'Cidade Teste', '23456-789'),
    ('38396cbf-9729-4f5e-9c2e-21cc4326c215', 'Praça Central', '789', 'Sala 202', 'Cidade Central', '34567-890'),
    ('e5bb4475-cb20-4a8f-8140-94ae03654623', 'Rua Exemplo', '123', 'Apto 101', 'Cidade Exemplo', '12345-678'),
    ('a4e9ea7a-16c4-4ad5-b277-9d1d80daf09c', 'Rua Exemplo', '123', 'Apto 101', 'Cidade Exemplo', '12345-678'),
    ('11c8e323-6894-4a66-8b7b-d400a1863239', 'Rua Exemplo', '123', 'Apto 101', 'Cidade Exemplo', '12345-678');

INSERT INTO forkup.usuario (id, nome, email, login, senha, tipo_usuario_id, endereco_id) VALUES
    ('6a12b853-4179-43ba-8e38-7187f1f5d363', 'João Silva', 'joao.silva@example.com', 'joao.silva',
     'SenhaForte1234@', '1e7b8f9e-1c2d-4a5b-8f9e-1c2d4a5b8f9e', '4a7f93ec-5940-4803-833b-540dd3fade4e'),
    ('809e9b65-d878-4bef-81bd-74379bf24eb3', 'Maria Oliveira', 'maria.oliveira@example.com', 'maria.oliveira',
     'SenhaForte456@', '2f8c9a0b-2d3e-4b6c-9a0b-2d3e4b6c9a0b', 'fb592482-462b-4902-a0f7-0b658374d28d'),
    ('704f1d39-f40a-4319-a56b-1c08278a24d2', 'Usuario a ser Alterado', 'usuario.alterado@example.com', 'usuario.alterado',
     'SenhaForte789@', '2f8c9a0b-2d3e-4b6c-9a0b-2d3e4b6c9a0b', '0e426136-f74a-4562-9818-2c46d25f0814'),
    ('59f598a2-8864-4738-b9b6-3b32c1c18ec9', 'Carlos Santos Usuário a Ser Excluido', 'carlos.santos@example.com', 'carlos.santos',
     'SenhaForte789@', '1e7b8f9e-1c2d-4a5b-8f9e-1c2d4a5b8f9e', '59f598a2-8864-4738-b9b6-3b32c1c18ec9'),
    ('615c8f57-6bf2-4426-a3bb-3a59faa70b95', 'Dono Restaurante 1', 'dono.restaurante1@example.com', 'dono.restaurante1',
     'SenhaForte789@', '2f8c9a0b-2d3e-4b6c-9a0b-2d3e4b6c9a0b', '974bfcd3-3261-47a1-a526-e47e192d2b5c'),
    ('0558b2af-a22a-4631-a608-02e6536f33c9', 'Dono Restaurante 2', 'dono.restaurante2@example.com', 'dono.restaurante2',
     'SenhaForte789@', '2f8c9a0b-2d3e-4b6c-9a0b-2d3e4b6c9a0b', 'd907095c-6c25-403e-a223-cde862685360'),
    ('344eef8e-f75f-4849-9530-9ab230ca413c', 'Dono Restaurante 3', 'dono.restaurante3@example.com', 'dono.restaurante3',
     'SenhaForte789@', '2f8c9a0b-2d3e-4b6c-9a0b-2d3e4b6c9a0b', '38396cbf-9729-4f5e-9c2e-21cc4326c215'),
    ('1bf2ab7b-a418-43e4-ad20-40a838e3d538', 'Dono Restaurante 4', 'dono.restaurante4@example.com', 'dono.restaurante4',
     'SenhaForte789@', '2f8c9a0b-2d3e-4b6c-9a0b-2d3e4b6c9a0b', 'e5bb4475-cb20-4a8f-8140-94ae03654623'),
     ('14436ce7-3d15-45ea-b7c1-9d626b4ddfbe', 'Dono Restaurante 5', 'dono.restaurante5@example.com', 'dono.restaurante5',
     'SenhaForte789@', '2f8c9a0b-2d3e-4b6c-9a0b-2d3e4b6c9a0b', 'a4e9ea7a-16c4-4ad5-b277-9d1d80daf09c'),
     ('dc67ce18-52ec-48ae-b19a-63d0b747c8dd', 'Dono Restaurante 6', 'dono.restaurante6@example.com', 'dono.restaurante6',
     'SenhaForte789@', '2f8c9a0b-2d3e-4b6c-9a0b-2d3e4b6c9a0b', '11c8e323-6894-4a66-8b7b-d400a1863239');

---
INSERT INTO forkup.endereco (id, logradouro, numero, complemento, cidade, cep) VALUES
    ('64774ab0-3822-4f8c-bba0-5e3a50a701be', 'Rua Exemplo', '123', 'Apto 101', 'Cidade Exemplo', '12345-678'),
    ('26e0eaea-7833-416b-90ca-7834ae62e648', 'Avenida Teste', '456', NULL, 'Cidade Teste', '23456-789'),
    ('ab55157c-40d5-4e78-b36b-90291fde3267', 'Praça Central', '789', 'Sala 202', 'Cidade Central', '34567-890');

INSERT INTO forkup.restaurante (id, nome, tipo_cozinha, horario_funcionamento, endereco_id, dono_id) VALUES
    ('d1f8c9a0-2d3e-4b6c-9a0b-2d3e4b6c9a0b', 'Restaurante Exemplo', 'Italiana', '10:00 - 22:00', '64774ab0-3822-4f8c-bba0-5e3a50a701be', '615c8f57-6bf2-4426-a3bb-3a59faa70b95'),
    ('e2f8c9a0-2d3e-4b6c-9a0b-2d3e4b6c9a0b', 'Restaurante Teste', 'Mexicana', '11:00 - 23:00', '26e0eaea-7833-416b-90ca-7834ae62e648', '0558b2af-a22a-4631-a608-02e6536f33c9'),
    ('36840a82-06a7-47d0-9e0c-a2e8517b6c6c', 'Restaurante A Ser Excluido', 'Chinesa', '09:00 - 21:00', 'ab55157c-40d5-4e78-b36b-90291fde3267', '14436ce7-3d15-45ea-b7c1-9d626b4ddfbe');

INSERT INTO forkup.item_cardapio (id, nome, descricao, preco, apenas_restaurante, foto_url, restaurante_id) VALUES
    ('750bb722-cd6a-4734-a406-64a0a35a0d98', 'Temaki Manter', 'Delicioso Temaki de Salmão em Cubos.', 25.00, FALSE, 'https://example.com/temaki_salmao.jpg', 'd1f8c9a0-2d3e-4b6c-9a0b-2d3e4b6c9a0b'),
    ('47605e32-4c3a-4bf2-aa6d-11108a92bf6c', 'Urumaki Salmão Alterar', 'Sushi de Salmão com alga nori.', 15.00, FALSE, 'https://example.com/urumaki_salmao.jpg', 'd1f8c9a0-2d3e-4b6c-9a0b-2d3e4b6c9a0b'),
    ('7d3767bc-c5d9-4e72-a0dd-5757446f9974', 'Sushi Variado A Ser Excluido', 'Seleção de sushi fresco com peixe e vegetais.', 30.00, FALSE, 'https://example.com/sushi_variado.jpg', 'd1f8c9a0-2d3e-4b6c-9a0b-2d3e4b6c9a0b');