package br.com.hyzed.hyzedapi.domain.image;

import jakarta.validation.constraints.NotBlank;

public record ImageDTO (
        @NotBlank(message = "URL should not be blank")
        String url) {
}
