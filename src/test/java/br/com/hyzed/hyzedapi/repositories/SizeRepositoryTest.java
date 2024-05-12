package br.com.hyzed.hyzedapi.repositories;

import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.domain.product.ProductDTO;
import br.com.hyzed.hyzedapi.domain.size.Size;
import br.com.hyzed.hyzedapi.domain.size.SizeDTO;
import br.com.hyzed.hyzedapi.domain.size.Sizes;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class SizeRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    SizeRepository sizeRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("Should return a stock of a product succesfully")
    void findBySizeAndProductCase1() {
        ProductDTO productDTO = new ProductDTO("Corta vento", new BigDecimal("180.00"));
        SizeDTO sizeDTO = new SizeDTO(Sizes.M,  10);

        Product product = this.createProduct(productDTO);
        this.createSize(sizeDTO, product);

        Optional<Size> result = this.sizeRepository.findBySizeAndProduct(Sizes.M, product);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not return a stock of a product")
    void findBySizeAndProductCase2() {
        ProductDTO productDTO = new ProductDTO("Corta vento", new BigDecimal("180.00"));

        Product product = this.createProduct(productDTO);

        Optional<Size> result = this.sizeRepository.findBySizeAndProduct(Sizes.M, product);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should return all stocks of a product")
    void findAllByProductCase1() {
        ProductDTO productDTO = new ProductDTO("Corta vento", new BigDecimal("180.00"));
        SizeDTO sizeDTO1 = new SizeDTO(Sizes.M,  10);
        SizeDTO sizeDTO2 = new SizeDTO(Sizes.G,  10);

        Product product = this.createProduct(productDTO);
        this.createSize(sizeDTO1, product);
        this.createSize(sizeDTO2, product);

        List<Size> result = this.sizeRepository.findAllByProduct(product);

        assertThat(result.size()).isEqualTo(2);

    }


    private Product createProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO);
        this.entityManager.persist(product);
        return product;
    }

    private Size createSize(SizeDTO sizeDTO, Product product) {
        Size size = new Size(sizeDTO);
        size.setProduct(product);
        this.entityManager.persist(size);
        return size;
    }

}