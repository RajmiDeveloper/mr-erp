package com.matias.mrerp.product_categories.service;

import com.matias.mrerp.product_categories.dto.request.CreateProductCategoryRequest;
import com.matias.mrerp.product_categories.dto.request.UpdateProductCategoryRequest;
import com.matias.mrerp.product_categories.dto.response.ProductCategoryResponse;
import com.matias.mrerp.product_categories.entity.ProductCategory;
import com.matias.mrerp.product_categories.repository.ProductCategoryRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository){
        this.productCategoryRepository = productCategoryRepository;
    }

    @Transactional
    public ProductCategoryResponse update(UUID id, UpdateProductCategoryRequest request){
        ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Category not found"));

        productCategory.setName(request.name());
        productCategory.setUpdatedAt();


        return toResponse(productCategory);
    }

    @Transactional
    public ProductCategoryResponse create(CreateProductCategoryRequest request){
        String normalizedName = request.name().trim();

        ProductCategory productCategory = new ProductCategory(
                normalizedName
        );

        ProductCategory savedCategory = productCategoryRepository.save(productCategory);

        return toResponse(savedCategory);
    }

    @Transactional(readOnly = true)
    public List<ProductCategoryResponse> findAll() {
        return productCategoryRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ProductCategoryResponse toResponse(ProductCategory product) {
        return new ProductCategoryResponse(
                product.getId(),
                product.getName(),
                product.getUpdatedAt()
        );
    }
}
