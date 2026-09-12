package com.matias.mrerp.product_categories.dto.response;

import java.time.Instant;
import java.util.UUID;

public record ProductCategoryResponse(
        UUID id,
        String name,
        Instant updatedAt
) {
}
