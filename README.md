# API CRUD RESTful - Pessoa e Trabalho

Esta é uma API RESTful desenvolvida em Java com Spring Boot que implementa operações CRUD (Create, Read, Update, Delete) para entidades Pessoa e Trabalho, mantendo um relacionamento entre elas.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 2.7.7
- Spring Data JPA
- H2 Database (banco de dados em memória)
- JUnit 5 e Mockito para testes

## Estrutura do Projeto

O projeto segue uma arquitetura em camadas:

- **Model**: Entidades JPA que representam as tabelas do banco de dados
- **DTO**: Objetos de transferência de dados para comunicação com o cliente
- **Repository**: Interfaces para acesso aos dados
- **Service**: Camada de negócio que implementa as regras e operações
- **Controller**: Endpoints REST da API

## Entidades

### Pessoa
- **ID**: Identificador único (gerado automaticamente)
- **CPF**: Número de CPF único
- **Idade**: Idade da pessoa
- **Relação**: Pertence a um Trabalho

### Trabalho
- **ID**: Identificador único (gerado automaticamente)
- **Nome**: Nome do trabalho
- **Endereço**: Endereço do local de trabalho
- **Relação**: Pode ter várias Pessoas associadas

## Endpoints da API

### Pessoa

- **GET /api/pessoas**: Lista todas as pessoas
- **GET /api/pessoas/{id}**: Busca uma pessoa pelo ID
- **POST /api/pessoas**: Cadastra uma nova pessoa
- **PUT /api/pessoas/{id}**: Atualiza uma pessoa existente
- **DELETE /api/pessoas/{id}**: Remove uma pessoa

### Trabalho

- **GET /api/trabalhos**: Lista todos os trabalhos
- **GET /api/trabalhos/{id}**: Busca um trabalho pelo ID
- **POST /api/trabalhos**: Cadastra um novo trabalho
- **PUT /api/trabalhos/{id}**: Atualiza um trabalho existente
- **DELETE /api/trabalhos/{id}**: Remove um trabalho

## Como Executar

1. Clone o repositório
2. Navegue até a pasta do projeto
3. Execute o comando: `mvn spring-boot:run`
4. A API estará disponível em `http://localhost:8080`

## Testes

Para executar os testes unitários, use o comando:

```
mvn test
```

## Console H2

O console do banco de dados H2 está disponível em:

```
http://localhost:8080/h2-console
```

Configurações de conexão:
- JDBC URL: jdbc:h2:mem:testdb
- Usuário: sa
- Senha: [vazio] 