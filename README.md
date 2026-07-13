# 🚀 ForkUp API

Projeto do **Tech Challenge da 2ª fase** da Pós-Graduação em **Arquitetura e Desenvolvimento Java**.

Esta versão do ForkUp foi refeita utilizando os princípios da Clean Architecture, promovendo baixo acoplamento entre as camadas, independência de frameworks e maior facilidade para testes e evolução da aplicação.

👨‍💻 Desenvolvido por: **LuisFelipeGM - RM: 371055**

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-green)
![Gradle](https://img.shields.io/badge/Gradle-8.x-02303A?logo=gradle)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-Migrations-CC0200?logo=flyway&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED?logo=docker&logoColor=white)
![Testcontainers](https://img.shields.io/badge/Testcontainers-Integration%20Tests-2496ED)
![JUnit 5](https://img.shields.io/badge/JUnit-5-25A162?logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-Tests-78A641)
![Clean Architecture](https://img.shields.io/badge/Architecture-Clean-success)

---
## Entregavéis:
2° Fase - [2 Fase - ForkUp - Tech Challenge.pdf](...)
- VIDEO APRESENTAÇÃO -
---

# 🎯 Visão Geral

O ForkUp é uma API REST desenvolvida em Java com Spring Boot para gerenciamento de restaurantes e pedidos.
Nesta etapa o projeto foi reconstruído seguindo os princípios da Clean Architecture, separando claramente regras de negócio, casos de uso, infraestrutura e interface.

A API permite:

- ✅ Cadastro de usuários
- ✅ Cadastro de tipos de usuários
- ✅ Cadastro de restaurantes
- ✅ Cadastro de endereços
- ✅ Gerenciamento do cardápio
- ✅ Paginação
- ✅ Validações
- ✅ Tratamento global de exceções
- ✅ Documentação automática
- ✅ Testes unitários
- ✅ Testes de integração utilizando Testcontainers

---

# 🏛️ Arquitetura
O projeto foi desenvolvido utilizando Clean Architecture, organizando a aplicação em camadas independentes.
```
                  ┌──────────────────────────┐
                  │      Infrastructure      │
                  │                          │
                  │     Controllers REST     │
                  │         DTOs             │
                  │        Mappers           │
                  └────────────┬─────────────┘
                               │
                               ▼
                  ┌──────────────────────────┐
                  │         Core             │
                  │                          │
                  │       Use Cases          │
                  │                          │
                  │ Domain Entities          │
                  │ Value Objects            │
                  │ Exceptions               │
                  │ Gateways (Interfaces)    │
                  └────────────┬─────────────┘
                               │
                               ▼
                  ┌──────────────────────────┐
                  │      Infrastructure      │
                  │                          │
                  │ Gateway Implementations  │
                  │ JPA Repositories         │
                  │ JPA Entities             │
                  │ Persistence Mappers      │
                  └────────────┬─────────────┘
                               │
                               ▼
                       PostgreSQL / Flyway
```
## Camadas
### Core

Contém toda a regra de negócio da aplicação.

- Entidades
- Casos de uso
- Gateways
- Exceptions
- Objetos de paginação

O Core não depende do Spring.

### Infrastructure

Responsável pela implementação das interfaces do Core.

- Controllers REST
- Repositórios JPA
- Mappers
- Configurações
- Persistência
- Tratamento de exceções
---

# 🛠️ Tecnologias

## Backend
- Java 21
- Spring Boot 4
- Spring Security *(criptografia de senha)*
- Spring Data JPA
- Hibernate Validator

## Banco de Dados
- PostgreSQL 15
- Flyway (migrations)

## Testes
- JUnit 5
- Mockito
- Testcontainers

## Documentação
- SpringDoc OpenAPI

## Ferramentas
- Lombok
- Gradle

## DevOps
- Docker
- Docker Compose

---

# 🐳 Arquitetura Docker

A aplicação é composta por dois serviços:

- **app** → API Spring Boot
- **postgres** → Banco de dados PostgreSQL

A comunicação entre eles ocorre via **rede interna do Docker**.

---

# ⚙️ Configuração

## 📋 Pré-requisitos

- Docker
- Docker Compose

---

### 1. Clonar o Repositório

```bash
git clone https://github.com/LuisFelipeGM/ForkUp-Clean-Architecture.git
cd ForkUp-Clean-Architecture
```
---

### 2. Configurar variáveis de ambiente

Copie o arquivo de exemplo:

```env
cp .env.example .env
```
No Windows (PowerShell):
```powershell
copy .env.example .env
```

Edite o arquivo `.env`:

```env
DB_NAME=forkup_db
DB_USER=admin
DB_PASSWORD=sua_senha
DB_PORT=5432
APP_PORT=3710
```
---
# 🚀 Execução 

## 🐳 Opção 1: Docker Compose (Recomendado)


```bash
docker compose up --build
```

A aplicação estará disponível em: `http://localhost:3710` *(Caso não tenha alterado a porta no .env)*

---
## 📦 Opção 2: Usando imagem do Docker Hub

```bash
docker pull luisfelipegm/forkup-clean-arch:latest
```

Executando manualmente:
```bash
docker run -p 3710:3710 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/forkup_db \
  -e SPRING_DATASOURCE_USERNAME=forkup_user \
  -e SPRING_DATASOURCE_PASSWORD=senha123 \
  luisfelipegm/forkup-clean-arch:latest
```
⚠️ Necessário ter um container PostgreSQL rodando.

---

# 🔌 API

## 🗄️ Banco de Dados

As migrações são gerenciadas automaticamente pelo Flyway:

```
src/main/resources/db/migration/
└── V001__init_forkup.sql
```

---

## 📄 Swagger (Documentação interativa)

Acesse:
👉 http://localhost:3710/swagger-ui.html

---

# 🧪 Testes

O projeto possui uma estratégia de testes dividida entre testes unitários e testes de integração, garantindo tanto a validação das regras de negócio quanto o funcionamento da aplicação como um todo.

## Testes Unitários

Os testes unitários validam os casos de uso e regras de negócio de forma isolada, utilizando JUnit 5 e Mockito para simular as dependências externas por meio de mocks.
Esses testes possuem execução rápida e garantem o comportamento correto da camada de negócio sem depender de banco de dados ou do contexto do Spring.

## Testes de Integração

Os testes de integração verificam o funcionamento da aplicação de ponta a ponta, incluindo a camada Web, os casos de uso, a persistência e o banco de dados.

Para isso, são utilizados:

- Spring Boot Test
- PostgreSQL
- Testcontainers

Durante a execução dos testes, um container PostgreSQL é iniciado automaticamente pelo Testcontainers. O mesmo container é reutilizado entre as classes de teste para reduzir o tempo de execução, proporcionando um ambiente próximo ao de produção e garantindo que as operações de persistência sejam validadas em um banco de dados real.

## Postman Collection

Uma collection está disponível no projeto:
- **Arquivo**: [ForkUp - Clean Arch.postman_collection.json](ForkUp%20-%20Clean%20Arch.postman_collection.json)
- Importe no Postman para testar todos os endpoints com cenários prontos.

---

# 🎯 Objetivos da Arquitetura
- Independência do Spring Framework
- Independência do banco de dados
- Facilidade para testes
- Separação clara de responsabilidades
- Baixo acoplamento
- Alta coesão
- Facilidade para evolução do sistema

---
# 🏗️ Estrutura do Projeto

O projeto segue um modelo em camadas:

```
com.fiap.forkup.clean.arch/
├── core/
|   ├── controller/              # Orquestra os Use Cases e adapta os dados entre Web e Core
|   ├── domain/                  # Entidades e regras de domínio
|   ├── dto/                     # DTOs utilizados pelo Core (Requests e Responses dos Use Cases)
|   ├── exception/               # Exceções de negócio
|   ├── gateway/                 # Interfaces (contratos) para comunicação com a infraestrutura
|   ├── mapper/                  # Conversão entre DTOs do Core e objetos de domínio
|   └── usecase/                 # Casos de uso e regras de negócio da aplicação
├── infra/
|   ├── config/                  # Configurações da aplicação (OpenAPI, Beans, etc.)
|   ├── persistence/
|   |   ├── jpa/
|   |   |   ├── entity/          # Entidades JPA
|   |   |   ├── gateway/         # Implementações dos Gateways utilizando JPA
|   |   |   ├── repository/      # Repositórios Spring Data JPA
|   |   ├── mapper/              # Conversão entre Domínio e Entidades JPA
|   ├── web/
|   |   ├── controller/          # Endpoints REST da API
|   |   ├── dto/                 # DTOs expostos pela API (Responses)
|   |   ├── exceptionhandler/    # Tratamento global de exceções e DTO de erro
|   |   ├── vo/                  # Objetos recebidos pela API (Requests)
```
---

# 📄 Licença
- Este projeto é acadêmico e de uso educacional.
