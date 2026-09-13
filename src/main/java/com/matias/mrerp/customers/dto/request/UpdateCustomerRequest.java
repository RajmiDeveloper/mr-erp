package com.matias.mrerp.customers.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateCustomerRequest(
        @NotBlank(message = "the name is mandatory")
        @Size(max = 150, message = "the name's max size is 150")
        String name,

        @Size(max = 100, message = "the identification's max size is 100")
        String identification,

        @NotNull(message = "the status is mandatory")
        Boolean active,

        UUID customerCategoryId
) {
}
