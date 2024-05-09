package br.com.hyzed.hyzedapi.domain.item;

import br.com.hyzed.hyzedapi.domain.size.Sizes;

public record ItemDTO(Long productId, Sizes size, Integer quantity) {
}
