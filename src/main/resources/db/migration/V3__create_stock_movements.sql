CREATE TABLE stock_movements (
    id UUID PRIMARY KEY,
    operation_id UUID NOT NULL,
    CONSTRAINT fk_operation_id
        FOREIGN KEY (operation_id)
            REFERENCES stock_movements_operations(id),
    product_id UUID NOT NULL,
    CONSTRAINT fk_product_id
        FOREIGN KEY (product_id)
            REFERENCES products(id),
    cost_price NUMERIC(15, 2) NOT NULL,
    quantity INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);