package br.com.hyzed.hyzedapi.repositories;

import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.domain.product.Size;
import br.com.hyzed.hyzedapi.domain.product.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductSizeRepository extends JpaRepository<Size, Long> {

    Optional<Size> findBySizeAndProduct(Sizes size, Product product);

    List<Size> findAllByProduct(Product product);
}
