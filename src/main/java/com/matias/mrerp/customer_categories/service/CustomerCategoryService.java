package com.matias.mrerp.customer_categories.service;

import com.matias.mrerp.customer_categories.dto.request.CreateCustomerCategoryRequest;
import com.matias.mrerp.customer_categories.dto.response.CustomerCategoryResponse;
import com.matias.mrerp.customer_categories.entity.CustomerCategory;
import com.matias.mrerp.customer_categories.repository.CustomerCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerCategoryService {
    private final CustomerCategoryRepository customerCategoryRepository;

    public CustomerCategoryService(CustomerCategoryRepository customerCategoryRepository) {
        this.customerCategoryRepository = customerCategoryRepository;
    }

    @Transactional
    public CustomerCategoryResponse create(CreateCustomerCategoryRequest request) {
        CustomerCategory category = new CustomerCategory(request.name().trim());
        return toResponse(customerCategoryRepository.save(category));
    }

    @Transactional(readOnly = true)
    public List<CustomerCategoryResponse> findAll() {
        return customerCategoryRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private CustomerCategoryResponse toResponse(CustomerCategory category) {
        return new CustomerCategoryResponse(
                category.getId(),
                category.getName(),
                category.getUpdatedAt()
        );
    }
}
