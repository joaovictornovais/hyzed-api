package br.com.hyzed.hyzedapi.repositories;

import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.domain.product.ProductDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@ActiveProfiles("teste")
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should find a product by name successfully")
    void findByNameCase1() {
        ProductDTO productDTO = new ProductDTO("Corta vento", new BigDecimal("180"));
        createProduct(productDTO);

        Optional<Product> response = productRepository.findByNameIgnoreCase(productDTO.name());

        assertThat(response.isPresent()).isTrue();
        assertThat(response.get().getName()).isEqualTo(productDTO.name());
    }

    private Product createProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO);
        this.entityManager.persist(product);
        return product;
    }

}