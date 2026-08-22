CREATE TABLE stock_movements_operations (
    id UUID PRIMARY KEY,
    reason VARCHAR(140),
    total_cost NUMERIC(15, 2) NOT NULL,
    total_quantity INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);