package com.matias.mrerp.product_categories.controller;


import com.matias.mrerp.product_categories.dto.request.CreateProductCategoryRequest;
import com.matias.mrerp.product_categories.dto.request.UpdateProductCategoryRequest;
import com.matias.mrerp.product_categories.dto.response.ProductCategoryResponse;
import com.matias.mrerp.product_categories.service.ProductCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product_categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService){
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public List<ProductCategoryResponse> findAll() {
        return productCategoryService.findAll();
    }

    @PostMapping
    public ResponseEntity<ProductCategoryResponse> create (
            @Valid @RequestBody CreateProductCategoryRequest request
    ){
        ProductCategoryResponse response = productCategoryService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> update (
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductCategoryRequest request
    ){
        ProductCategoryResponse response = productCategoryService.update(id, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
