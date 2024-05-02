package br.com.hyzed.hyzedapi.repositories;

import br.com.hyzed.hyzedapi.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
