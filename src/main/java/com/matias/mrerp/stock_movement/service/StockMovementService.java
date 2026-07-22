package com.matias.mrerp.stock_movement.service;

import com.matias.mrerp.products.repository.ProductRepository;
import com.matias.mrerp.products.entity.Product;
import com.matias.mrerp.stock_movement.dto.request.CreateStockMovementRequest;
import com.matias.mrerp.stock_movement.dto.response.StockMovementResponse;
import com.matias.mrerp.stock_movement.entity.StockMovement;
import com.matias.mrerp.stock_movement.repository.StockMovementRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;

    public StockMovementService(
            StockMovementRepository stockMovementRepository,
            ProductRepository productRepository
    ){
        this.stockMovementRepository = stockMovementRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public StockMovementResponse create(CreateStockMovementRequest request){
        StockMovement stockMovement = new StockMovement(
                request.productId(),
                request.costPrice(),
                request.quantity()
        );

        Product product = productRepository.findById(stockMovement.getProdudtId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (stockMovement.getQuantity() < 0 && Math.abs(stockMovement.getQuantity()) > product.getCurrentStock()){
            throw new RuntimeException("Negative Quantity is not allowed");
        }

        product.setCurrentStock(product.getCurrentStock() + stockMovement.getQuantity());
        StockMovement saved = stockMovementRepository.save(stockMovement);

        return toResponse(saved);
    }

    private StockMovementResponse toResponse(StockMovement stockMovement){
        return new StockMovementResponse(
                stockMovement.getProdudtId(),
                stockMovement.getCostPrice(),
                stockMovement.getQuantity()
        );
    }
}
