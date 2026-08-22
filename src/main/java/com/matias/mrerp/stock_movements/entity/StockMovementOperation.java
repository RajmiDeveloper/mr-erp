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
@Table(name = "stock_movements_operations")
public class StockMovementOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(length = 140)
    private String reason;

    @Column(
            name = "total_cost",
            precision = 15,
            scale = 2,
            nullable = false
    )
    private BigDecimal totalCost;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected StockMovementOperation() {
    }

    public StockMovementOperation(
            String reason,
            BigDecimal totalCost,
            Integer totalQuantity
    ) {
        this.reason = reason;
        this.totalCost = totalCost;
        this.totalQuantity = totalQuantity;
    }

    public UUID getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}