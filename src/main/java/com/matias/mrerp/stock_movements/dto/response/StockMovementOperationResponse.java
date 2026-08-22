package com.matias.mrerp.stock_movements.dto.response;

import com.matias.mrerp.stock_movements.dto.response.StockMovementResponse;

import java.util.List;

public record StockMovementOperationResponse (
    List<StockMovementResponse> movements
){}
