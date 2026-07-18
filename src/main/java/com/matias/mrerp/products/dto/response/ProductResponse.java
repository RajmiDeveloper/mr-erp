package com.matias.mrerp.products.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String code,
        String name,
        String description,
        BigDecimal salePrice,
        BigDecimal costPrice,
        Integer currentStock,
        boolean active
) {
}
