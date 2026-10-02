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

Verifique com:

```bash
java -version
docker --version
git --version
```

---

# 🐘 Banco de Dados

O projeto utiliza PostgreSQL 15 através do Docker.

O `docker-compose.yml` cria automaticamente o banco:

```text
Banco:     raizes_nordeste
Usuário:   postgres
Senha:     postgres
Porta:     5432
```

A aplicação utiliza:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/raizes_nordeste
    username: postgres
    password: postgres
```

As tabelas são criadas/atualizadas pelo Hibernate através de:

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
```

Principais tabelas:

```text
tb_usuarios
tb_produtos
unidades
estoques
tb_pedidos
itens_pedido
```

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

Logs:

```bash
docker compose logs -f postgres
```

Para parar:

```bash
docker compose stop
```

Para remover o container:

```bash
docker compose down
```

Para remover também o volume e apagar todos os dados:

```bash
docker compose down -v
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

Estrutura:

```text
raizes-do-nordeste/
├── data.sql
├── docker-compose.yml
├── pom.xml
└── src/
    └── main/
        ├── java/
        └── resources/
            └── application.yml
```

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

# 🔐 Autenticação

Login:

```http
POST /api/v3/auth/login
```

Exemplo:

```json
{
  "email": "cliente.teste@raizes.com",
  "senha": "123456"
}
```

Endpoints protegidos exigem:

```http
Authorization: Bearer SEU_TOKEN
```

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

# 📌 Principais Endpoints

## 🔐 Autenticação

```http
POST /api/v3/auth/login
```

## 👤 Usuários

```http
GET /api/usuarios
POST /api/usuarios
GET /api/usuarios/{id}
```

## 📦 Produtos

```http
GET /api/produtos
POST /api/produtos
GET /api/produtos/{id}
DELETE /api/produtos/{id}
GET /api/produtos/categoria/{categoria}
```

## 🏪 Unidades e estoque

```http
GET /api/unidades/{unidadeId}/produtos
```

Filtro:

```http
GET /api/unidades/{unidadeId}/produtos?categoria=PRATOS_PRINCIPAIS
```

O estoque relaciona produtos e unidades através de:

```text
estoques
```

com:

```text
produto_id
unidade_id
quantidade
```

## 🛒 Pedidos

```http
GET /api/pedidos
POST /api/pedidos
GET /api/pedidos/{id}
DELETE /api/pedidos/{id}
GET /api/pedidos/usuario/{usuarioId}
GET /api/pedidos/status/{status}
```

Status:

```text
AGUARDANDO_PAGAMENTO
EM_PREPARO
PRONTO
ENTREGUE
CANCELADO
```

## 💳 Pagamentos

```http
POST /api/v3/pedidos/{pedidoId}/pagamentos
```

Exemplo:

```json
{
  "formaPagamento": "PIX",
  "valor": 48
}
```

Pedido em `AGUARDANDO_PAGAMENTO` pode passar para `EM_PREPARO` após pagamento aprovado ou para `CANCELADO` em caso de pagamento recusado, conforme a regra implementada.

## 🔄 Atualização de status

```http
PATCH /api/v3/pedidos/{pedidoId}/status
```

Exemplo:

```json
{
  "novoStatus": "PRONTO"
}
```

Perfis autorizados:

```text
COZINHA
ATENDENTE
GERENTE
```

Fluxo esperado:

```text
AGUARDANDO_PAGAMENTO
        ↓
      pagamento
        ↓
   EM_PREPARO
        ↓
      PRONTO
        ↓
    ENTREGUE
```

---

# 🧪 Cenário de testes recomendado

Após executar o `data.sql`:

| Pedido | Status | Valor | Uso |
|---:|---|---:|---|
| 1 | AGUARDANDO_PAGAMENTO | R$ 48,00 | Testar pagamento |
| 2 | EM_PREPARO | R$ 24,00 | Testar PATCH → PRONTO |
| 3 | PRONTO | R$ 32,00 | Testar PATCH → ENTREGUE |
| 4 | ENTREGUE | R$ 24,00 | Testar pedido finalizado |

Para testar atualização de status, faça login com:

```text
cozinha.teste@raizes.com
```

Senha:

```text
123456
```

Depois:

```http
PATCH /api/v3/pedidos/2/status
```

Body:

```json
{
  "novoStatus": "PRONTO"
}
```

---

# 🧪 Testando permissões

Usuários autorizados:

```text
cozinha.teste@raizes.com
atendente.teste@raizes.com
gerente.teste@raizes.com
```

Usuário sem permissão para alteração de status:

```text
cliente.teste@raizes.com
```

Senha:

```text
123456
```

Ao tentar alterar status com `CLIENTE`, a API deverá retornar:

```text
403 Forbidden
```

---

# 📮 Postman / Insomnia

Na pasta:

```text
/docs/collections
```

podem ser armazenadas coleções exportadas para Postman ou Insomnia.

---

# 🗂️ Estrutura principal

```text
raizes-do-nordeste/
│
├── docker-compose.yml
├── data.sql
├── pom.xml
├── README.md
│
├── docs/
│   └── collections/
│
└── src/
    └── main/
        ├── java/
        │   └── backend/
        │       ├── config/
        │       ├── controller/
        │       ├── dto/
        │       ├── enums/
        │       ├── exception/
        │       ├── mapper/
        │       ├── model/
        │       ├── repository/
        │       ├── security/
        │       └── service/
        │
        └── resources/
            └── application.yml
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

Configuração utilizada pela aplicação:

```text
Host:     localhost
Porta:    5432
Database: raizes_nordeste
Usuário:  postgres
Senha:    postgres
```

---

# 🧹 Reset completo do ambiente

Para começar novamente do zero:

```bash
docker compose down -v
```

Depois:

```bash
docker compose up -d
```

Inicie a aplicação:

```bash
mvn spring-boot:run
```

O Hibernate recriará/atualizará as tabelas.

Depois execute novamente:

```text
data.sql
```

no PostgreSQL.

> ⚠️ `docker compose down -v` remove o volume do PostgreSQL e todos os dados armazenados nele.

---

# 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos e de aprendizado.
