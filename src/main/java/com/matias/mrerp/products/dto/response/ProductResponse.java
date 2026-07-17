package com.matias.mrerp.products.dto.response;

import java.util.UUID;

public record ProductResponse(
        UUID id,
        String code,
        String name
) {
}
