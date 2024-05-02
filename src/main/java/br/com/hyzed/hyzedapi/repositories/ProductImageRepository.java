package br.com.hyzed.hyzedapi.repositories;

import br.com.hyzed.hyzedapi.domain.product.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<Image, Long> {
}
