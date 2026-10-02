-- ============================================================
-- RAÍZES DO NORDESTE - BANCO DE DADOS DE TESTE
-- PostgreSQL
--
-- ATENÇÃO:
-- Este script FAZ RESET dos dados das tabelas do projeto antes
-- de inserir os dados de teste.
--
-- Senha dos usuários de teste: 123456
--
-- Tabelas utilizadas:
--   tb_usuarios
--   unidades
--   tb_produtos
--   estoques
--   tb_pedidos
--   itens_pedido
--
-- O script também deixa pronto o relacionamento:
--   Produto <-> Estoque <-> Unidade
--
-- Pode ser executado manualmente no pgAdmin.
-- ============================================================

BEGIN;

-- ============================================================
-- 1. RESET DOS DADOS
-- ============================================================
-- ATENÇÃO: isto apaga os registros atuais dessas tabelas.
-- As tabelas/estruturas não são apagadas.
--
-- CASCADE garante que os relacionamentos entre as tabelas
-- não impeçam a limpeza.
--
-- RESTART IDENTITY reinicia os IDs para 1.

TRUNCATE TABLE
    itens_pedido,
    estoques,
    tb_pedidos,
    tb_produtos,
    unidades,
    tb_usuarios
RESTART IDENTITY CASCADE;


-- ============================================================
-- 2. EXTENSÃO PARA GERAR HASHES COMPATÍVEIS COM BCRYPT
-- ============================================================
CREATE EXTENSION IF NOT EXISTS pgcrypto;


-- ============================================================
-- 3. USUÁRIOS
-- ============================================================
-- Todos usam a senha:
-- 123456
--
-- Os três primeiros perfis permitem testar o PATCH de status:
-- COZINHA, ATENDENTE e GERENTE.
--
-- O CLIENTE permite testar o acesso negado (403).

INSERT INTO tb_usuarios
    (nome, email, senha, perfil, consentimento, data_criacao)
VALUES
    (
        'Cliente Teste',
        'cliente.teste@raizes.com',
        crypt('123456', gen_salt('bf')),
        'CLIENTE',
        true,
        CURRENT_TIMESTAMP
    ),
    (
        'Cozinha Teste',
        'cozinha.teste@raizes.com',
        crypt('123456', gen_salt('bf')),
        'COZINHA',
        true,
        CURRENT_TIMESTAMP
    ),
    (
        'Atendente Teste',
        'atendente.teste@raizes.com',
        crypt('123456', gen_salt('bf')),
        'ATENDENTE',
        true,
        CURRENT_TIMESTAMP
    ),
    (
        'Gerente Teste',
        'gerente.teste@raizes.com',
        crypt('123456', gen_salt('bf')),
        'GERENTE',
        true,
        CURRENT_TIMESTAMP
    );


-- ============================================================
-- 4. UNIDADES
-- ============================================================
-- Unidade 1 e 2: ativas.
-- Unidade 3: inativa, para testar a validação do pedido.

INSERT INTO unidades
    (nome, endereco, ativa)
VALUES
    (
        'Unidade Centro',
        'Rua Principal, 100 - Centro',
        true
    ),
    (
        'Unidade Beira-Mar',
        'Av. Beira-Mar, 500',
        true
    ),
    (
        'Unidade Temporariamente Inativa',
        'Rua do Mercado, 20',
        false
    );


-- ============================================================
-- 5. PRODUTOS
-- ============================================================

INSERT INTO tb_produtos
    (nome, descricao, preco, estoque, categoria)
VALUES
    (
        'Baião de Dois',
        'Arroz, feijão e ingredientes tradicionais do Nordeste',
        24.00,
        20,
        'PRATOS_PRINCIPAIS'
    ),
    (
        'Carne de Sol',
        'Carne de sol acompanhada de ingredientes regionais',
        32.00,
        15,
        'PRATOS_PRINCIPAIS'
    ),
    (
        'Cuscuz Nordestino',
        'Cuscuz tradicional nordestino',
        12.00,
        25,
        'PRATOS_PRINCIPAIS'
    ),
    (
        'Suco de Cajá',
        'Bebida de cajá',
        8.00,
        30,
        'BEBIDAS'
    ),
    (
        'Camiseta Raízes',
        'Camiseta temática do projeto',
        45.00,
        10,
        'VESTUARIO'
    ),
    (
        'Peça de Cerâmica',
        'Peça decorativa artesanal',
        60.00,
        8,
        'DECORACAO'
    );


-- ============================================================
-- 6. ESTOQUE POR UNIDADE
-- ============================================================
-- Este é o relacionamento que já foi utilizado no projeto:
--
--   estoques.produto_id -> tb_produtos.id
--   estoques.unidade_id -> unidades.id
--
-- Cada registro representa a quantidade de um produto
-- disponível em uma determinada unidade.
--
-- Os valores abaixo já consideram os pedidos de teste
-- cadastrados posteriormente.

INSERT INTO estoques
    (quantidade, produto_id, unidade_id)
VALUES
    -- Unidade Centro
    (16, 1, 1), -- Baião de Dois
    (14, 2, 1), -- Carne de Sol
    (15, 3, 1), -- Cuscuz
    (20, 4, 1), -- Suco de Cajá
    (5, 5, 1),  -- Camiseta

    -- Unidade Beira-Mar
    (7, 1, 2),  -- Baião de Dois
    (7, 2, 2),  -- Carne de Sol
    (8, 3, 2),  -- Cuscuz
    (15, 4, 2), -- Suco de Cajá
    (5, 6, 2);  -- Peça de Cerâmica


-- ============================================================
-- 7. PEDIDOS DE TESTE
-- ============================================================
--
-- Pedido 1:
--   AGUARDANDO_PAGAMENTO
--   R$ 48,00
--   Pode ser usado para testar o endpoint de pagamento.
--
-- Pedido 2:
--   EM_PREPARO
--   R$ 24,00
--   Pode ser usado para testar PATCH -> PRONTO.
--
-- Pedido 3:
--   PRONTO
--   R$ 32,00
--   Pode ser usado para testar PATCH -> ENTREGUE.
--
-- Pedido 4:
--   ENTREGUE
--   R$ 24,00
--   Pode ser usado para testar tentativas de alteração
--   de pedidos que já chegaram ao estado final.

INSERT INTO tb_pedidos
    (
        usuario_id,
        unidade_id,
        canal_pedido,
        forma_pagamento,
        status,
        valor_total,
        data_pedido
    )
VALUES
    (
        1,
        1,
        'TOTEM',
        'PIX',
        'AGUARDANDO_PAGAMENTO',
        48.00,
        CURRENT_TIMESTAMP - INTERVAL '4 hours'
    ),
    (
        1,
        1,
        'TOTEM',
        'PIX',
        'EM_PREPARO',
        24.00,
        CURRENT_TIMESTAMP - INTERVAL '3 hours'
    ),
    (
        1,
        2,
        'TOTEM',
        'PIX',
        'PRONTO',
        32.00,
        CURRENT_TIMESTAMP - INTERVAL '2 hours'
    ),
    (
        1,
        2,
        'TOTEM',
        'PIX',
        'ENTREGUE',
        24.00,
        CURRENT_TIMESTAMP - INTERVAL '1 hour'
    );


-- ============================================================
-- 8. ITENS DOS PEDIDOS
-- ============================================================
-- Nome confirmado pela estrutura atual do banco:
-- itens_pedido
--
-- Colunas:
--   id
--   preco_unitario
--   quantidade
--   subtotal
--   pedido_id
--   produto_id

INSERT INTO itens_pedido
    (
        preco_unitario,
        quantidade,
        subtotal,
        pedido_id,
        produto_id
    )
VALUES
    (24.00, 2, 48.00, 1, 1),
    (24.00, 1, 24.00, 2, 1),
    (32.00, 1, 32.00, 3, 2),
    (24.00, 1, 24.00, 4, 1);


-- ============================================================
-- 9. GARANTIR SEQUÊNCIAS CORRETAS
-- ============================================================
-- Como os registros foram inseridos sem informar IDs,
-- o PostgreSQL já controla as sequências automaticamente.
--
-- Estes SELECTs deixam as sequências sincronizadas mesmo
-- se o script for adaptado posteriormente para IDs explícitos.

SELECT setval(
    pg_get_serial_sequence('tb_usuarios', 'id'),
    COALESCE((SELECT MAX(id) FROM tb_usuarios), 1),
    true
);

SELECT setval(
    pg_get_serial_sequence('unidades', 'id'),
    COALESCE((SELECT MAX(id) FROM unidades), 1),
    true
);

SELECT setval(
    pg_get_serial_sequence('tb_produtos', 'id'),
    COALESCE((SELECT MAX(id) FROM tb_produtos), 1),
    true
);

SELECT setval(
    pg_get_serial_sequence('estoques', 'id'),
    COALESCE((SELECT MAX(id) FROM estoques), 1),
    true
);

SELECT setval(
    pg_get_serial_sequence('tb_pedidos', 'id'),
    COALESCE((SELECT MAX(id) FROM tb_pedidos), 1),
    true
);

SELECT setval(
    pg_get_serial_sequence('itens_pedido', 'id'),
    COALESCE((SELECT MAX(id) FROM itens_pedido), 1),
    true
);


-- ============================================================
-- 10. CONFIRMAÇÃO
-- ============================================================

SELECT 'Usuários cadastrados: ' || COUNT(*) AS resultado
FROM tb_usuarios;

SELECT 'Unidades cadastradas: ' || COUNT(*) AS resultado
FROM unidades;

SELECT 'Produtos cadastrados: ' || COUNT(*) AS resultado
FROM tb_produtos;

SELECT 'Registros de estoque: ' || COUNT(*) AS resultado
FROM estoques;

SELECT 'Pedidos cadastrados: ' || COUNT(*) AS resultado
FROM tb_pedidos;

SELECT 'Itens de pedidos cadastrados: ' || COUNT(*) AS resultado
FROM itens_pedido;


COMMIT;
