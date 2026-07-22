package com.matias.mrerp.stock_movement.entity;

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

    @Column(name = "product_id",nullable = false, updatable = false)
    private UUID produdtId;

    @Column(name = "cost_price",precision = 15, scale = 2, nullable = false)
    private BigDecimal costPrice;

    @Column(name = "created_at",nullable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private Integer quantity;

    public UUID getProdudtId() {
        return produdtId;
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

    public void setProdudtId(UUID produdtId) {
        this.produdtId = produdtId;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }
    public StockMovement(UUID produdtId, BigDecimal costPrice, Integer quantity){
        this.produdtId = produdtId;
        this.costPrice = costPrice;
        this.quantity = quantity;
    }
}
