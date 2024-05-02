package br.com.hyzed.hyzedapi.domain.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDTO(
        @NotBlank
        String name,
        @NotNull
        BigDecimal price
) {
}
