# Dados de teste — Raízes do Nordeste

Este pacote contém um `data.sql` para preparar um banco PostgreSQL
com dados completos para testes da API.

## ⚠️ O script faz RESET dos dados

Ao executar `data.sql`, os registros existentes nas seguintes tabelas
serão apagados:

- `itens_pedido`
- `estoques`
- `tb_pedidos`
- `tb_produtos`
- `unidades`
- `tb_usuarios`

As tabelas e suas estruturas NÃO são apagadas.
Portanto, use esse arquivo em um banco de desenvolvimento/testes.

---

## O que será criado

### Usuários

Todos usam a senha:

```text
123456
```

| Perfil | E-mail | Finalidade |
|---|---|---|
| CLIENTE | `cliente.teste@raizes.com` | Testar usuário comum |
| COZINHA | `cozinha.teste@raizes.com` | Testar atualização de status |
| ATENDENTE | `atendente.teste@raizes.com` | Testar atualização de status |
| GERENTE | `gerente.teste@raizes.com` | Testar atualização de status |

Os três últimos são úteis para testar:

```text
PATCH /api/v3/pedidos/{pedidoId}/status
```

O usuário `CLIENTE` pode ser usado para confirmar que um perfil sem
permissão recebe `403 Forbidden`.

---

## Unidades

| ID | Unidade | Ativa |
|---:|---|---|
| 1 | Unidade Centro | Sim |
| 2 | Unidade Beira-Mar | Sim |
| 3 | Unidade Temporariamente Inativa | Não |

A unidade 3 serve para testar a regra de unidade inativa.

---

## Produtos

| ID | Produto | Preço | Categoria |
|---:|---|---:|---|
| 1 | Baião de Dois | 24,00 | PRATOS_PRINCIPAIS |
| 2 | Carne de Sol | 32,00 | PRATOS_PRINCIPAIS |
| 3 | Cuscuz Nordestino | 12,00 | PRATOS_PRINCIPAIS |
| 4 | Suco de Cajá | 8,00 | BEBIDAS |
| 5 | Camiseta Raízes | 45,00 | VESTUARIO |
| 6 | Peça de Cerâmica | 60,00 | DECORACAO |

---

## Estoque por unidade

O script também deixa pronto o relacionamento usado no projeto:

```text
tb_produtos
     |
     | produto_id
     v
  estoques
     ^
     | unidade_id
     |
 unidades
```

Cada registro de `estoques` relaciona um produto a uma unidade e
informa sua quantidade disponível.

Exemplo:

```text
produto_id = 1
unidade_id = 1
quantidade = 16
```

Isso representa o Baião de Dois disponível na Unidade Centro.

---

## Pedidos preparados

| Pedido | Status | Valor | Unidade |
|---:|---|---:|---|
| 1 | `AGUARDANDO_PAGAMENTO` | R$ 48,00 | Centro |
| 2 | `EM_PREPARO` | R$ 24,00 | Centro |
| 3 | `PRONTO` | R$ 32,00 | Beira-Mar |
| 4 | `ENTREGUE` | R$ 24,00 | Beira-Mar |

---

## Como executar pelo pgAdmin

1. Abra o pgAdmin.
2. Selecione o banco utilizado pela aplicação.
3. Abra o **Query Tool**.
4. Abra o arquivo `data.sql`.
5. Execute o script inteiro.
6. Confira as mensagens de confirmação no final.

Você deverá encontrar resultados semelhantes a:

```text
Usuários cadastrados: 4
Unidades cadastradas: 3
Produtos cadastrados: 6
Registros de estoque: 10
Pedidos cadastrados: 4
Itens de pedidos cadastrados: 4
```

---


Se quiser que o Spring Boot execute o arquivo automaticamente,
adicione ao `application.properties`:

```properties
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
```

### ⚠️ Atenção

Com `spring.sql.init.mode=always`, o reset será executado quando
o Spring inicializar a aplicação.

Isso significa que os dados cadastrados manualmente no banco serão
apagados e substituídos pelos dados de teste a cada inicialização.

---

## Resumo dos testes preparados

### Autenticação

- CLIENTE
- COZINHA
- ATENDENTE
- GERENTE

### Unidades

- unidade ativa
- unidade inativa
- duas unidades com produtos/estoque

### Produtos

- pratos principais
- bebida
- vestuário
- decoração

### Estoque

- relacionamento produto/unidade
- quantidades diferentes por unidade

### Pedidos

- aguardando pagamento
- em preparo
- pronto
- entregue

### Endpoints

O banco preparado permite testar:

```text
POST /api/v3/auth/login

GET /api/unidades/{unidadeId}/produtos

POST /api/pedidos

GET /api/pedidos

GET /api/pedidos/{id}

GET /api/pedidos/usuario/{usuarioId}

GET /api/pedidos/status/{status}

POST /api/v3/pedidos/{pedidoId}/pagamentos

PATCH /api/v3/pedidos/{pedidoId}/status
```
