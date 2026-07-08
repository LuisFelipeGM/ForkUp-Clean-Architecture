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
    ('3f9d0b1c-3e4f-5c7d-8b1c-3e4f5c7d0b1c', 'Administrador');