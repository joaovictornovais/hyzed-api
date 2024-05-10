package br.com.hyzed.hyzedapi.domain.order;

import jakarta.validation.constraints.NotNull;

public record OrderStatusDTO(
        @NotNull(message = "OrderStatus should not be null")
        OrderStatus orderStatus) {
}
