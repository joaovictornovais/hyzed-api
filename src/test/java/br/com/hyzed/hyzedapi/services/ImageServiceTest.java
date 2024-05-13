package br.com.hyzed.hyzedapi.services;

import br.com.hyzed.hyzedapi.domain.image.Image;
import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.exceptions.EntityNotFoundException;
import br.com.hyzed.hyzedapi.exceptions.InvalidArgumentsException;
import br.com.hyzed.hyzedapi.repositories.ImageRepository;
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
import static org.mockito.Mockito.when;

class ImageServiceTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @InjectMocks
    ImageService imageService;

    @Mock
    ImageRepository imageRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should associate a image with a product succesfully")
    void saveImageCase1() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        Image image = new Image(1L, "http://api.hyzed.com/images/1.jpeg", product);

        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));

        Optional<Image> response = imageRepository.findById(1L);

        assertThat(response.isPresent()).isTrue();
        assertThat(response.get().getProduct()).isEqualTo(product);
    }

    @Test
    @DisplayName("Should remove a image from a product successfully")
    void removeImageFromAProductCase1() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        Image image = new Image(1L, "http://api.hyzed.com/images/1.jpeg", product);
        product.getImages().add(image);

        product.getImages().remove(image);

        assertThat(product.getImages().contains(image)).isFalse();

    }

    @Test
    @DisplayName("Should throw a exception because the image are not associate with product")
    void removeImageFromAProductCase2() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        Image image = new Image(1L, "http://api.hyzed.com/images/1.jpeg", product);

        Exception thrown = Assertions.assertThrows(InvalidArgumentsException.class, () -> {
            if (!product.getImages().contains(image))
                throw new InvalidArgumentsException("This image is not associated with this product.");
        });

        Assertions.assertEquals(thrown.getMessage(), "This image is not associated with this product.");
    }

    @Test
    @DisplayName("Should find a image by ID successfully")
    void findImageByIdCase1() {
        Image image = new Image(1L, "http://api.hyzed.com/images/1.jpeg");

        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));

        Optional<Image> response = imageRepository.findById(1L);

        assertThat(response.isPresent()).isTrue();
        assertThat(response.get()).isEqualTo(image);
    }

    @Test
    @DisplayName("Should throw a exception when not find a image by Id")
    void findImageByIdCase2() {
        Exception thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            if (imageRepository.findById(1L).isEmpty())
                throw new EntityNotFoundException("Image not found");
        });

        Assertions.assertEquals(thrown.getMessage(), "Image not found");
    }

    @Test
    @DisplayName("Should remove all images from a product")
    void removeAllImagesFromProductCase1() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        Image image1 = new Image(1L, "http://api.hyzed.com/images/1.jpeg");
        Image image2 = new Image(1L, "http://api.hyzed.com/images/2.jpeg");

        product.getImages().addAll(List.of(image1, image2));

        assertThat(product.getImages().contains(image1)).isTrue();
        assertThat(product.getImages().contains(image2)).isTrue();

        product.getImages().removeAll(List.of(image1, image2));

        assertThat(product.getImages().contains(image1)).isFalse();
        assertThat(product.getImages().contains(image2)).isFalse();
    }


}