package com.matias.mrerp.stock_movements.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateStockMovementOperationRequest (
    @Size(max = 150, message = "The reason can't be bigger than 150 characters")
    String reason,

    @NotNull
    List<CreateStockMovementRequest> movements
){ }
