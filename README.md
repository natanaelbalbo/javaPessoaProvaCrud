
---

# API RESTful CRUD – Pessoa e Trabalho

Esta aplicação é uma API RESTful construída com Java e Spring Boot, que oferece operações CRUD (Criar, Ler, Atualizar e Deletar) para as entidades Pessoa e Trabalho, com vínculo entre elas.

## Tecnologias Empregadas

- Java 17  
- Spring Boot 2.7.7  
- Spring Data JPA  
- Banco de Dados H2 (em memória)  
- JUnit 5 e Mockito para testes automatizados  

## Organização do Projeto

A aplicação segue uma arquitetura baseada em camadas:

- **Model**: Classes de entidade JPA que representam as tabelas do banco de dados  
- **DTO (Data Transfer Object)**: Objetos para troca de dados entre cliente e servidor  
- **Repository**: Interfaces responsáveis pelo acesso ao banco de dados  
- **Service**: Lógica de negócio e implementação das operações  
- **Controller**: Camada que define os endpoints REST da aplicação  

## Entidades

### Pessoa
- **ID**: Identificador único (gerado automaticamente)  
- **CPF**: Cadastro de Pessoa Física (único)  
- **Idade**: Idade da pessoa  
- **Relacionamento**: Está associada a um Trabalho  

### Trabalho
- **ID**: Identificador único (gerado automaticamente)  
- **Nome**: Nome do trabalho ou profissão  
- **Endereço**: Local onde o trabalho é realizado  
- **Relacionamento**: Pode estar vinculado a múltiplas Pessoas  

## Endpoints da API

### Pessoa

- **GET /api/pessoas**: Retorna a lista de todas as pessoas  
- **GET /api/pessoas/{id}**: Retorna uma pessoa específica pelo ID  
- **POST /api/pessoas**: Cria um novo registro de pessoa  
- **PUT /api/pessoas/{id}**: Atualiza as informações de uma pessoa existente  
- **DELETE /api/pessoas/{id}**: Remove uma pessoa do sistema  

### Trabalho

- **GET /api/trabalhos**: Retorna todos os trabalhos cadastrados  
- **GET /api/trabalhos/{id}**: Busca um trabalho específico pelo ID  
- **POST /api/trabalhos**: Adiciona um novo trabalho  
- **PUT /api/trabalhos/{id}**: Atualiza um trabalho existente  
- **DELETE /api/trabalhos/{id}**: Exclui um trabalho do sistema  

## Como Rodar o Projeto

1. Faça o clone do repositório  
2. Acesse a pasta do projeto pelo terminal  
3. Execute o comando: `mvn spring-boot:run`  
4. A API estará rodando em: `http://localhost:8080`  

## Executando os Testes

Para rodar os testes unitários, utilize:

```
mvn test
```

## Acessando o Console do H2

O console do banco de dados em memória H2 pode ser acessado através de:

```
http://localhost:8080/h2-console
```

Parâmetros de conexão:
- JDBC URL: jdbc:h2:mem:testdb  
- Usuário: sa  
- Senha: *(deixe em branco)*

---
