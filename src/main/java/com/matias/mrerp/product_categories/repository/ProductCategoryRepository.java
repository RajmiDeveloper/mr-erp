package com.matias.mrerp.product_categories.repository;


import com.matias.mrerp.product_categories.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {

}
