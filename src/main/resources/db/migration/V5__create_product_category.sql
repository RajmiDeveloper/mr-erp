CREATE TABLE product_category (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

ALTER TABLE products
    ADD COLUMN product_category_id UUID;

ALTER TABLE products
    ADD CONSTRAINT fk_product_product_category
        FOREIGN KEY (product_category_id)
            REFERENCES product_category(id);