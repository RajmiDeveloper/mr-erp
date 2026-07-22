package com.matias.mrerp.products.service;

import com.matias.mrerp.products.dto.request.CreateProductRequest;
import com.matias.mrerp.products.dto.request.UpdateProductRequest;
import com.matias.mrerp.products.dto.response.ProductResponse;
import com.matias.mrerp.products.entity.Product;
import com.matias.mrerp.products.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse update(UUID id, UpdateProductRequest request){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setCostPrice(request.costPrice());
        product.setSalePrice(request.salePrice());
        product.setCurrentStock(request.currentStock());
        product.setActive(request.active());
        product.setUpdatedAt();


        return toResponse(product);
    }


    @Transactional
    public ProductResponse create(CreateProductRequest request){
        String normalizedCode = request.code().trim().toUpperCase();
        String normalizedName = request.name().trim();

        if(productRepository.existsByCode(normalizedCode)){
            throw new IllegalArgumentException(
                    "This code already exist"
            );
        }

        Product product = new Product(
                normalizedCode,
                normalizedName,
                request.description(),
                request.salePrice(),
                request.costPrice()

        );

        Product savedProduct = productRepository.save(product);

        return toResponse(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getDescription(),
                product.getSalePrice(),
                product.getCostPrice(),
                product.getCurrentStock(),
                product.isActive()
        );
    }
}
