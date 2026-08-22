package com.matias.mrerp.stock_movements.controller;

import com.matias.mrerp.stock_movements.dto.request.CreateStockMovementOperationRequest;
import com.matias.mrerp.stock_movements.dto.request.CreateStockMovementRequest;
import com.matias.mrerp.stock_movements.dto.response.StockMovementOperationResponse;
import com.matias.mrerp.stock_movements.dto.response.StockMovementResponse;
import com.matias.mrerp.stock_movements.service.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/stockMovement")
public class StockMovementController {
    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService){
        this.stockMovementService = stockMovementService;
    }

    @PostMapping
    public ResponseEntity<StockMovementOperationResponse> create (
            @Valid @RequestBody CreateStockMovementOperationRequest request
    ){
        StockMovementOperationResponse response = stockMovementService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
