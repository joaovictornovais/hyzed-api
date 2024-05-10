package br.com.hyzed.hyzedapi.domain.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDTO(
        @NotBlank(message = "Name should not blank")
        String name,
        @NotNull(message = "Price should not be null")
        BigDecimal price
) {
}
