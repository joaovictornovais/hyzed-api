package br.com.hyzed.hyzedapi.repositories;

import br.com.hyzed.hyzedapi.domain.product.Image;
import br.com.hyzed.hyzedapi.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByProduct(Product product);
}
