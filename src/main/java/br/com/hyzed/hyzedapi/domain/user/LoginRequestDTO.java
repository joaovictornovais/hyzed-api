package br.com.hyzed.hyzedapi.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password) {
}
