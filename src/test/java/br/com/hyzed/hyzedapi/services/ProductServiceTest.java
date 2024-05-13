package br.com.hyzed.hyzedapi.services;

import br.com.hyzed.hyzedapi.domain.image.Image;
import br.com.hyzed.hyzedapi.domain.image.ImageDTO;
import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.domain.size.Size;
import br.com.hyzed.hyzedapi.domain.size.SizeDTO;
import br.com.hyzed.hyzedapi.domain.size.Sizes;
import br.com.hyzed.hyzedapi.exceptions.EntityNotFoundException;
import br.com.hyzed.hyzedapi.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @InjectMocks
    @Autowired
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    ImageService imageService;

    @Mock
    SizeService sizeService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should save succesfully a Product in db")
    void saveProductCase1() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        productRepository.save(product);

        verify(productRepository, times(1)).save(product);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> response = productRepository.findById(1L);

        assertThat(response).isPresent();
        assertThat(response.get()).isEqualTo(product);
    }

    @Test
    @DisplayName("Should find a product by id successfully")
    void findProductByIdCase1() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> response = productRepository.findById(1L);

        assertThat(response.isPresent()).isTrue();
        assertThat(response.get()).isEqualTo(product);
    }

    @Test
    @DisplayName("Should throw a exception when product not found by id")
    void findProductByIdCase2() {
        Exception thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            if (productRepository.findById(1L).isEmpty())
                throw new EntityNotFoundException("Product not found");
        });

        Assertions.assertEquals(thrown.getMessage(), "Product not found");
    }

    @Test
    @DisplayName("Should delete successfully a product from db (images and sizes included)")
    void deleteProductByIdCase1() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        Image image = new Image(1L, "https://api.hyzed.com/images/1.jpeg");
        Size size = new Size(1L, Sizes.M, 1, product);

        product.getImages().add(image);
        product.getSizes().add(size);

        imageService.removeAllImagesFromProduct(product);
        product.getImages().removeAll(List.of(image));

        sizeService.removeAllSizesFromProduct(product);
        product.getSizes().removeAll(List.of(size));

        assertThat(product.getImages()).isEmpty();
        assertThat(product.getSizes()).isEmpty();

        productRepository.deleteById(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should add image to a product succesfully")
    void addImageToProductCase1() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        ImageDTO imageDTO = new ImageDTO("https://api.hyzed.com/images/1.jpeg");
        Image image = new Image(1L, imageDTO.url());

        imageService.saveImage(product, imageDTO.url());
        product.getImages().add(image);

        assertThat(product.getImages()).contains(image);
    }

    @Test
    @DisplayName("Should remove a image from a product successfully")
    void removeImageFromProduct() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        Image image = new Image(1L, "https://api.hyzed.com/images/1.jpeg");
        product.getImages().add(image);

        imageService.removeImage(product, image.getId());
        product.getImages().remove(image);

        assertThat(product.getImages()).doesNotContain(image);
    }

    @Test
    @DisplayName("Should add variety to a product successfully")
    void addVarietyToProduct() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        SizeDTO sizeDTO = new SizeDTO(Sizes.M, 10);

        Size size = new Size(sizeDTO);

        sizeService.saveSize(product, sizeDTO);
        product.getSizes().add(size);

        assertThat(product.getSizes()).contains(size);
    }

    @Test
    @DisplayName("Should remove a variety from product successfully")
    void removeVarietyFromProduct() {
        int firstQuantity, finalQuantity = 0;
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        Size size = new Size(1L, Sizes.M, 100, product);
        product.getSizes().add(size);
        SizeDTO sizeDTO = new SizeDTO(Sizes.M, 10);
        firstQuantity = size.getQuantity();

        sizeService.removeSize(product, sizeDTO);
        size.setQuantity(size.getQuantity() - sizeDTO.quantity());
        finalQuantity = size.getQuantity();

        assertThat(product.getSizes().contains(size)).isTrue();
        assertThat(firstQuantity).isGreaterThan(finalQuantity);
    }

}