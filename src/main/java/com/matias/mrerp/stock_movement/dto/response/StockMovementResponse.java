package com.matias.mrerp.stock_movement.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record StockMovementResponse(
        UUID productId,
        BigDecimal costPrice,
        Integer quantity
) {
}
