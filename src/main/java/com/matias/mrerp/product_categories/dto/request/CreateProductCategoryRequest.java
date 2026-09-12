package com.matias.mrerp.product_categories.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProductCategoryRequest(
    @NotBlank(message = "the name is mandatory")
    @Size(max = 50, message = "the name's max size is 50")
    String name
) {

}
