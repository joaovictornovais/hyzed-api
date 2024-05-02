package br.com.hyzed.hyzedapi.domain.product;

import jakarta.validation.constraints.NotNull;

public record SizeDTO(
        @NotNull
        Sizes size,
        @NotNull
        Integer quantity) {
}
