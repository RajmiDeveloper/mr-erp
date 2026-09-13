package com.matias.mrerp.customer_categories.repository;

import com.matias.mrerp.customer_categories.entity.CustomerCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerCategoryRepository extends JpaRepository<CustomerCategory, UUID> {
}
