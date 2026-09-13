package com.matias.mrerp.customers.repository;

import com.matias.mrerp.customers.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    boolean existsByCode(String code);
}
