package com.matias.mrerp.stock_movements.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "stock_movements")
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "operation_id",nullable = false, updatable = false)
    private UUID operationId;

    @Column(name = "product_id",nullable = false, updatable = false)
    private UUID productId;

    public UUID getOperationId() {
        return operationId;
    }

    @Column(name = "cost_price",precision = 15, scale = 2, nullable = false)
    private BigDecimal costPrice;

    @Column(name = "created_at",nullable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private Integer quantity;

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }
    public StockMovement(UUID operationId, UUID productId, BigDecimal costPrice, Integer quantity){
        this.operationId = operationId;
        this.productId = productId;
        this.costPrice = costPrice;
        this.quantity = quantity;
    }
}
