package com.matias.mrerp.customers.service;

import com.matias.mrerp.customer_categories.entity.CustomerCategory;
import com.matias.mrerp.customer_categories.repository.CustomerCategoryRepository;
import com.matias.mrerp.customers.dto.request.CreateCustomerRequest;
import com.matias.mrerp.customers.dto.request.UpdateCustomerRequest;
import com.matias.mrerp.customers.dto.response.CustomerResponse;
import com.matias.mrerp.customers.entity.Customer;
import com.matias.mrerp.customers.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerCategoryRepository customerCategoryRepository;

    public CustomerService(
            CustomerRepository customerRepository,
            CustomerCategoryRepository customerCategoryRepository
    ) {
        this.customerRepository = customerRepository;
        this.customerCategoryRepository = customerCategoryRepository;
    }

    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {
        String normalizedCode = request.code().trim().toUpperCase();

        if (customerRepository.existsByCode(normalizedCode)) {
            throw new IllegalArgumentException("This code already exists");
        }

        Customer customer = new Customer(
                normalizedCode,
                request.name().trim(),
                normalizeIdentification(request.identification())
        );
        customer.setCustomerCategory(findCategory(request.customerCategoryId()));

        return toResponse(customerRepository.save(customer));
    }

    @Transactional
    public CustomerResponse update(UUID id, UpdateCustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setName(request.name().trim());
        customer.setIdentification(normalizeIdentification(request.identification()));
        customer.setActive(request.active());
        customer.setCustomerCategory(findCategory(request.customerCategoryId()));
        customer.setUpdatedAt();

        return toResponse(customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll() {
        return customerRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private CustomerCategory findCategory(UUID categoryId) {
        if (categoryId == null) return null;

        return customerCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Customer category not found"));
    }

    private String normalizeIdentification(String identification) {
        if (identification == null) return null;

        String normalizedIdentification = identification.trim();
        return normalizedIdentification.isEmpty() ? null : normalizedIdentification;
    }

    private CustomerResponse toResponse(Customer customer) {
        CustomerCategory category = customer.getCustomerCategory();

        return new CustomerResponse(
                customer.getId(),
                customer.getCode(),
                customer.getName(),
                customer.getIdentification(),
                customer.isActive(),
                category != null ? category.getId() : null,
                category != null ? category.getName() : null
        );
    }
}
