package br.com.hyzed.hyzedapi.domain.item;

import br.com.hyzed.hyzedapi.domain.product.ProductDTO;
import br.com.hyzed.hyzedapi.domain.size.Sizes;

import java.math.BigDecimal;

public record ItemOrderDTO(ProductDTO product, Integer quantity, BigDecimal subtotal, Sizes size) {
}
