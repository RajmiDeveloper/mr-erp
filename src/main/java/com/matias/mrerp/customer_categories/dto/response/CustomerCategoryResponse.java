package com.matias.mrerp.customer_categories.dto.response;

import java.time.Instant;
import java.util.UUID;

public record CustomerCategoryResponse(
        UUID id,
        String name,
        Instant updatedAt
) {
}
