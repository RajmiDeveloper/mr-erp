CREATE TABLE selling_movements (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL,
    CONSTRAINT fk_product_id
        FOREIGN KEY (product_id)
            REFERENCES products(id),
    price_rate NUMERIC(15, 2) NOT NULL,
    quantity INTEGER NOT NULL,
    discount FLOAT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);