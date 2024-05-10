package br.com.hyzed.hyzedapi.domain.size;

import jakarta.validation.constraints.NotNull;

public record SizeDTO(
        @NotNull(message = "Size should not be null")
        Sizes size,
        @NotNull(message = "Quantity should not be null")
        Integer quantity) {
}
