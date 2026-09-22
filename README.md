# API RESTful - Raízes do Nordeste

Esta é uma aplicação RESTful desenvolvida com **Spring Boot** para o gerenciamento de pedidos e usuários. O projeto conta com documentação interativa integrada via **Swagger**.

---

## 🚀 Tecnologias Utilizadas

- **Java 21** (ou versão utilizada)
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Validation**
- **PostgreSQL/Docker** 
- **SpringDoc OpenAPI (Swagger UI)**
- **Maven**

---

## 🛠️ Como Executar o Projeto

### Pré-requisitos
- **Java Development Kit (JDK)** instalado (versão 17 ou superior)
- **Apache Maven** instalado (ou utilizar o wrapper `./mvnw`)

### Passos para Execução

1. **Clonar o repositório:**
   ```bash
   git clone https://github.com/AnaCBrandao/raizes-do-nordeste.git
   cd raizes-do-nordeste

2. **Configurar as Variáveis de Ambiente (opcional):**
Caso haja configurações no arquivo src/main/resources/application.yml, certifique-se de validar as credenciais do banco de dados.

3. **Compilar e construir a aplicação:**
   ```bash
    mvn clean package

4. **Executar a aplicação:**
   ```bash
    mvn spring-boot:run
   
A aplicação estará acessível em: http://localhost:8080

---

## 📖 Documentação da API (Swagger UI)
Com a aplicação em execução, acesse a documentação interativa para visualizar, testar e consultar todos os endpoints disponíveis:

👉 URL do Swagger UI: http://localhost:8080/swagger-ui/index.html

## 📌 Endpoints Principais
### 📦 Pedidos (/api/pedidos)
GET /api/pedidos - Lista todos os pedidos

POST /api/pedidos - Cadastra um novo pedido

GET /api/pedidos/{id} - Busca pedido por ID

DELETE /api/pedidos/{id} - Remove um pedido por ID

GET /api/pedidos/usuario/{usuarioId} - Lista pedidos por usuário

GET /api/pedidos/status/{status} - Lista pedidos por status

### 👤 Usuários (/api/usuarios)
GET /api/usuarios - Lista todos os usuários

POST /api/usuarios - Cadastra um novo usuário

GET /api/usuarios/{id} - Busca usuário por ID

---

## 🧪 Testes com Postman / Insomnia
Na pasta /docs ou /collection deste repositório, você encontra a coleção exportada do Postman contendo as requisições prontas para teste.

## 📄 Licença
Este projeto é voltado para fins acadêmicos e de aprendizado.
