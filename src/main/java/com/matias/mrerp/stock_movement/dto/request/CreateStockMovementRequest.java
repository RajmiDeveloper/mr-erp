package com.matias.mrerp.stock_movement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateStockMovementRequest (

        UUID productId,

        @NotNull(message = "the cost price is mandatory")
        @PositiveOrZero(message = "the cost price cannot be negative")
        BigDecimal costPrice,

        @NotNull(message = "quantity is mandatory")
        Integer quantity


){

}