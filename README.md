# API RESTful - Raízes do Nordeste

API RESTful desenvolvida com **Spring Boot 3**, **Java 21** e **PostgreSQL** para gerenciamento de usuários, produtos, unidades, estoque, pedidos e pagamentos.
O projeto possui autenticação utilizando **JWT**, controle de acesso por perfil de usuário e documentação interativa através do **Swagger UI**.

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Security**
- **JWT (JSON Web Token)**
- **BCrypt**
- **Spring Validation**
- **PostgreSQL 15**
- **Docker / Docker Compose**
- **SpringDoc OpenAPI / Swagger UI**
- **Maven**

---

# 📋 Pré-requisitos

- Java JDK 21 ou superior
- Docker e Docker Compose
- Git
- Maven instalado ou Maven Wrapper (`mvnw`)

# 🐘 Banco de Dados

O projeto utiliza PostgreSQL 15 através do Docker.
O `docker-compose.yml` cria automaticamente o banco.

---

# 🐳 Subindo o PostgreSQL com Docker

Na raiz do projeto:

```bash
docker compose up -d
```

Verifique:

```bash
docker ps
```

O container deverá aparecer como:

```text
raizes_nordeste_db
```

Para parar:

```bash
docker compose stop
```

> ⚠️ `docker compose down -v` apaga os dados armazenados no PostgreSQL.

---

# ▶️ Executando a aplicação

## 1. Clonar

```bash
git clone https://github.com/AnaCBrandao/raizes-do-nordeste.git
cd raizes-do-nordeste
```

## 2. Iniciar o PostgreSQL

```bash
docker compose up -d
```

## 3. Compilar

Com Maven:

```bash
mvn clean package
```

Ou Maven Wrapper:

Linux/macOS:

```bash
./mvnw clean package
```

Windows:

```bash
mvnw.cmd clean package
```

## 4. Executar

Com Maven:

```bash
mvn spring-boot:run
```

Ou Maven Wrapper:

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080
```

---

# 🗃️ Preparando os dados de teste

O projeto possui o arquivo:

```text
data.sql
```

Ele deve ser executado manualmente no PostgreSQL depois que a aplicação tiver sido iniciada pelo menos uma vez.
O Hibernate cria as tabelas. O `data.sql` apenas prepara os dados de teste.

O script:

1. Remove os dados existentes;
2. Reinicia os IDs;
3. Cadastra usuários;
4. Cadastra unidades;
5. Cadastra produtos;
6. Configura estoque por unidade;
7. Cadastra pedidos;
8. Cadastra itens dos pedidos.

> ⚠️ O script executa `TRUNCATE`. Use-o somente em ambiente de desenvolvimento/teste.

---

# 👥 Usuários de teste

Todos usam a senha:

```text
123456
```

| Perfil | E-mail |
|---|---|
| CLIENTE | cliente.teste@raizes.com |
| COZINHA | cozinha.teste@raizes.com |
| ATENDENTE | atendente.teste@raizes.com |
| GERENTE | gerente.teste@raizes.com |

Os perfis `COZINHA`, `ATENDENTE` e `GERENTE` são úteis para testar atualização de status. O perfil `CLIENTE` pode ser usado para testar acesso não autorizado.

---

# 📖 Swagger UI

Com a aplicação em execução:

```text
http://localhost:8080/swagger-ui/index.html
```

Para autorizar:

1. Execute o login.
2. Copie o JWT.
3. Clique em **Authorize**.
4. Informe `Bearer SEU_TOKEN`.
5. Clique em **Authorize**.
6. Feche a janela.

---


# 📮 Postman / Insomnia

Na pasta:

```text
/docs/collections
```

Consta uma coleção .json para testes em Postman ou Insomnia.

---

# 🗂️ Estrutura principal

```text
raizes-do-nordeste/
│
├── docker-compose.yml -> Sobe o banco de dados relacional
├── pom.xml            -> Configurações e dependências do Spring
├── README.md          -> Instruções para executar 
├── .env.example       -> Modelo para variáveis de ambiente
│
├── docs/
│   └── collections/   -> Coleção de testes (.json)
│
└── src/
    └── main/
        ├── java/
        │   └── backend/            
        │       ├── api/            -> Controllers/mappers
        │       ├── application/    -> Dtos/services
        │       ├── domain/         -> Enums/Entidades/Exceções
        │       ├── infrastructure/ -> Repository/Segurança/Configurações
        │
        └── resources/
            └── application.yml      -> Configurações do Postgres
            └── data.sql             -> Arquivo para popular o banco
```

---

# 🔧 Configuração do PostgreSQL

O `docker-compose.yml` utiliza:

```yaml
services:
  postgres:
    image: postgres:15-alpine
    container_name: raizes_nordeste_db
    environment:
      POSTGRES_DB: raizes_nordeste
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
```

---


# 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos e de aprendizado.
