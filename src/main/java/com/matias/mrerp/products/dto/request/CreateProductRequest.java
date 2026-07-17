package com.matias.mrerp.products.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductRequest (

        @NotBlank(message = "the message is mandatory")
        @Size(max = 50, message = "the message's max size is 50")
        String code,

        @NotBlank(message = "the name is mandatory")
        @Size(max = 150, message = "the name's max size is 150")
        String name,

        @Size(max = 500, message = "the description's max size is 500")
        String description,

        @NotNull(message = "the sale price is mandatory")
        @PositiveOrZero(message = "the sale price cannot be negative")
        BigDecimal salePrice,

        @NotNull(message = "the cost price is mandatory")
        @PositiveOrZero(message = "the cost price cannot be negative")
        BigDecimal costPrice
){


}
