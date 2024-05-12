package br.com.hyzed.hyzedapi.repositories;

import br.com.hyzed.hyzedapi.domain.image.Image;
import br.com.hyzed.hyzedapi.domain.image.ImageDTO;
import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.domain.product.ProductDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ImageRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("Should return a List of Images from a Product successfully")
    void findAllByProductCase1() {
        ProductDTO productDTO = new ProductDTO("Corta vento", new BigDecimal("180.00"));
        Product product = this.createProduct(productDTO);

        ImageDTO imageDTO1 = new ImageDTO("http://api.hyzed.com/images/image1.jpeg");
        ImageDTO imageDTO2 = new ImageDTO("http://api.hyzed.com/images/image2.jpeg");

        this.createImage(imageDTO1, product);
        this.createImage(imageDTO2, product);

        List<Image> result = imageRepository.findAllByProduct(product);

        assertThat(result.size()).isEqualTo(2);

    }

    private Image createImage(ImageDTO imageDTO, Product product) {
        Image image = new Image(imageDTO);
        image.setProduct(product);
        this.entityManager.persist(image);
        return image;
    }

    private Product createProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO);
        this.entityManager.persist(product);
        return product;
    }
}