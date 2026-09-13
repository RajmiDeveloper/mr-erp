package com.matias.mrerp.customers.entity;

import com.matias.mrerp.customer_categories.entity.CustomerCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 100)
    private String identification;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_category_id")
    private CustomerCategory customerCategory;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    protected Customer() {
    }

    public Customer(String code, String name, String identification) {
        this.code = code;
        this.name = name;
        this.identification = identification;
    }

    public UUID getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getIdentification() { return identification; }
    public boolean isActive() { return active; }
    public CustomerCategory getCustomerCategory() { return customerCategory; }

    public void setName(String name) { this.name = name; }
    public void setIdentification(String identification) { this.identification = identification; }
    public void setActive(boolean active) { this.active = active; }
    public void setCustomerCategory(CustomerCategory customerCategory) { this.customerCategory = customerCategory; }
    public void setUpdatedAt() { this.updatedAt = Instant.now(); }
}
