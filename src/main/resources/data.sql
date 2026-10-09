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
-- ============================================================

BEGIN;

-- ATENÇÃO: isto apaga os registros atuais dessas tabelas.
-- As tabelas/estruturas não são apagadas.

TRUNCATE TABLE
    itens_pedido,
    estoques,
    tb_pedidos,
    tb_produtos,
    unidades,
    tb_usuarios
RESTART IDENTITY CASCADE;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

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

INSERT INTO estoques
    (quantidade, produto_id, unidade_id)
VALUES
    (16, 1, 1), 
    (14, 2, 1), 
    (15, 3, 1), 
    (20, 4, 1), 
    (5, 5, 1),  

    (7, 1, 2),  
    (7, 2, 2),  
    (8, 3, 2),  
    (15, 4, 2), 
    (5, 6, 2);  

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