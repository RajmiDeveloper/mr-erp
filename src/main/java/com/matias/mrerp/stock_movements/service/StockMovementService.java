package com.matias.mrerp.stock_movements.service;

import com.matias.mrerp.products.repository.ProductRepository;
import com.matias.mrerp.products.entity.Product;
import com.matias.mrerp.stock_movements.dto.request.CreateStockMovementRequest;
import com.matias.mrerp.stock_movements.dto.request.CreateStockMovementOperationRequest;
import com.matias.mrerp.stock_movements.dto.response.StockMovementOperationResponse;
import com.matias.mrerp.stock_movements.dto.response.StockMovementResponse;
import com.matias.mrerp.stock_movements.entity.StockMovement;
import com.matias.mrerp.stock_movements.repository.StockMovementRepository;
import com.matias.mrerp.stock_movements.repository.StockMovementOperationRepository;
import com.matias.mrerp.stock_movements.entity.StockMovementOperation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@Service
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final StockMovementOperationRepository operationRepository;
    private final ProductRepository productRepository;

    public StockMovementService(
            StockMovementRepository stockMovementRepository,
            StockMovementOperationRepository operationRepository,
            ProductRepository productRepository
    ){
        this.stockMovementRepository = stockMovementRepository;
        this.operationRepository = operationRepository;
        this.productRepository = productRepository;
    }



    private void validateMovement(CreateStockMovementRequest request){

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (request.quantity() < 0 && Math.abs(request.quantity()) > product.getCurrentStock()){
            throw new RuntimeException("Negative Stock is not allowed");
        }
    }



    @Transactional
    public StockMovementOperationResponse create(CreateStockMovementOperationRequest requests){

        for (CreateStockMovementRequest movement : requests.movements()){
            validateMovement(movement);
        }
        StockMovementOperation operation = new StockMovementOperation(
                requests.reason(),
                BigDecimal.ZERO,
                0
        );

        StockMovementOperation savedOperation =
                operationRepository.save(operation);

        StockMovement saved;
        for (CreateStockMovementRequest request : requests.movements()) {


            Product product = productRepository.findById(request.productId()).orElseThrow();;
            product.setCurrentStock(product.getCurrentStock() + request.quantity());

            StockMovement movement = new StockMovement(
                    savedOperation.getId(),
                    request.productId(),
                    request.costPrice(),
                    request.quantity()
            );

            saved = stockMovementRepository.save(movement);

        }
        return toResponse(requests);
    }

    private StockMovementOperationResponse toResponse(CreateStockMovementOperationRequest requests){
        List<StockMovementResponse> list_response = new ArrayList<>();
        for (CreateStockMovementRequest request : requests.movements()){
            list_response.add(new StockMovementResponse(
                    request.productId(),
                    request.costPrice(),
                    request.quantity()
            ));
        }

        return new StockMovementOperationResponse(list_response);
    }
}
