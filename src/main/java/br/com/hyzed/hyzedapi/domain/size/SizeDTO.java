package br.com.hyzed.hyzedapi.domain.size;

import jakarta.validation.constraints.NotNull;

public record SizeDTO(
        @NotNull
        Sizes size,
        @NotNull
        Integer quantity) {
}
