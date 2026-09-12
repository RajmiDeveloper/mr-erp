package com.matias.mrerp.products.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import com.matias.mrerp.product_categories.entity.ProductCategory;
import java.util.UUID;


import java.time.Instant;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, length = 50, unique = true)
    private String code;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(name = "sale_price", precision = 15, scale = 2, nullable = false)
    private BigDecimal salePrice;

    @Column(name = "cost_price",precision = 15, scale = 2, nullable = false)
    private BigDecimal costPrice;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "created_at",nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    @Column(name = "current_stock", nullable = false)
    private int currentStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt() {
        this.updatedAt = Instant.now();
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    protected Product() {
    }

    public Product(String code, String name, String description,
                   BigDecimal salePrice, BigDecimal costPrice) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.currentStock = 1;
    }
}
