package br.com.hyzed.hyzedapi.domain.item;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record ProductsDTO(
        @NotNull(message = "products should not be null")
        Set<ItemDTO> products) {
}
