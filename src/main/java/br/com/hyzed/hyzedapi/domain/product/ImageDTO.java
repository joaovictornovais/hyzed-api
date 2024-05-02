package br.com.hyzed.hyzedapi.domain.product;

import jakarta.validation.constraints.NotBlank;

public record ImageDTO (
        @NotBlank
        String url) {
}
