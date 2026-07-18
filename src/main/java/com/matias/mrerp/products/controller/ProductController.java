package com.matias.mrerp.products.controller;

import com.matias.mrerp.products.dto.request.CreateProductRequest;
import com.matias.mrerp.products.dto.request.UpdateProductRequest;
import com.matias.mrerp.products.dto.response.ProductResponse;
import com.matias.mrerp.products.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create (
            @Valid @RequestBody CreateProductRequest request
    ){
        ProductResponse response = productService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update (
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductRequest request
    ){
        ProductResponse response = productService.update(id, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
