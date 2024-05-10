package br.com.hyzed.hyzedapi.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @Email(message = "email should be well-formed. example: your_email@example.com")
        @NotBlank(message = "email should not be blank")
        String email,
        @NotBlank(message = "password should not be blank")
        String password) {
}
