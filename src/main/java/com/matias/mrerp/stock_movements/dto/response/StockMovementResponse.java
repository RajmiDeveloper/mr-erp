package com.matias.mrerp.stock_movements.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record StockMovementResponse(
        UUID productId,
        BigDecimal costPrice,
        Integer quantity
) {
}
