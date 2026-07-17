package com.matias.mrerp.products.service;

import com.matias.mrerp.products.dto.request.CreateProductRequest;
import com.matias.mrerp.products.dto.response.ProductResponse;
import com.matias.mrerp.products.entity.Product;
import com.matias.mrerp.products.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
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

        return new ProductResponse(
                savedProduct.getId(),
                savedProduct.getCode(),
                savedProduct.getName()
        );
    }
}
