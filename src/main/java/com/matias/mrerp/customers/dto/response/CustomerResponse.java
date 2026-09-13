package com.matias.mrerp.customers.dto.response;

import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String code,
        String name,
        String identification,
        boolean active,
        UUID customerCategoryId,
        String customerCategoryName
) {
}
