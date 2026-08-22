CREATE TABLE price_List (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);


CREATE TABLE product_category_price_list (
    product_category_id UUID NOT NULL,
    price_list_id UUID NOT NULL,
    increase_percentage NUMERIC(7, 4) NOT NULL DEFAULT 0,
    CONSTRAINT pk_product_category_price_list
      PRIMARY KEY (product_category_id, price_list_id),
    CONSTRAINT fk_icpl_product_category
      FOREIGN KEY (product_category_id)
          REFERENCES product_category(id),
    CONSTRAINT fk_icpl_price_list
      FOREIGN KEY (price_list_id)
          REFERENCES price_list(id),
    CONSTRAINT chk_icpl_increase_percentage
      CHECK (increase_percentage >= 0),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);