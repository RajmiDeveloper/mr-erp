INSERT INTO products (
    id,
    code,
    name,
    description,
    sale_price,
    cost_price,
    current_stock,
    active,
    created_at,
    updated_at
)
VALUES
    (
        gen_random_uuid(),
        'PLA-NEGRO',
        'Filamento PLA negro',
        'Rollo de PLA de 1 kg',
        18000.00,
        12000.00,
        10,
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        gen_random_uuid(),
        'PLA-BLANCO',
        'Filamento PLA blanco',
        'Rollo de PLA de 1 kg',
        17500.00,
        11500.00,
        8,
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );