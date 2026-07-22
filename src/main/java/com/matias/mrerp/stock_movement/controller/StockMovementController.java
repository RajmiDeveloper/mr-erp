package com.matias.mrerp.stock_movement.controller;

import com.matias.mrerp.products.dto.request.CreateProductRequest;
import com.matias.mrerp.products.dto.response.ProductResponse;
import com.matias.mrerp.stock_movement.dto.request.CreateStockMovementRequest;
import com.matias.mrerp.stock_movement.dto.response.StockMovementResponse;
import com.matias.mrerp.stock_movement.entity.StockMovement;
import com.matias.mrerp.stock_movement.service.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/stockMovement")
public class StockMovementController {
    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService){
        this.stockMovementService = stockMovementService;
    }

    @PostMapping
    public ResponseEntity<StockMovementResponse> create (
            @Valid @RequestBody CreateStockMovementRequest request
    ){
        StockMovementResponse response = stockMovementService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
