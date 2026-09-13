package com.matias.mrerp.customer_categories.controller;

import com.matias.mrerp.customer_categories.dto.request.CreateCustomerCategoryRequest;
import com.matias.mrerp.customer_categories.dto.response.CustomerCategoryResponse;
import com.matias.mrerp.customer_categories.service.CustomerCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer_categories")
public class CustomerCategoryController {
    private final CustomerCategoryService customerCategoryService;

    public CustomerCategoryController(CustomerCategoryService customerCategoryService) {
        this.customerCategoryService = customerCategoryService;
    }

    @GetMapping
    public List<CustomerCategoryResponse> findAll() {
        return customerCategoryService.findAll();
    }

    @PostMapping
    public ResponseEntity<CustomerCategoryResponse> create(
            @Valid @RequestBody CreateCustomerCategoryRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerCategoryService.create(request));
    }
}
