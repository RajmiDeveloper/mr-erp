package com.matias.mrerp.products.repository;

import com.matias.mrerp.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByCode(String code);

}
