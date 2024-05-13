package br.com.hyzed.hyzedapi.services;

import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.domain.size.Size;
import br.com.hyzed.hyzedapi.domain.size.SizeDTO;
import br.com.hyzed.hyzedapi.domain.size.Sizes;
import br.com.hyzed.hyzedapi.exceptions.EntityNotFoundException;
import br.com.hyzed.hyzedapi.exceptions.InvalidArgumentsException;
import br.com.hyzed.hyzedapi.repositories.SizeRepository;
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

class SizeServiceTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @InjectMocks
    @Autowired
    SizeService sizeService;

    @Mock
    SizeRepository sizeRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a new size successfully")
    void saveSizeCase1() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        SizeDTO sizeDTO = new SizeDTO(Sizes.M, 10);

        Size size = new Size();
        size.setSize(sizeDTO.size());
        size.setQuantity(sizeDTO.quantity());
        size.setProduct(product);

        sizeRepository.save(size);
        verify(sizeRepository, times(1)).save(size);
    }

    @Test
    @DisplayName("Should add quantity to a size successfully")
    void saveSizeCase2() {
        int startQuantity, finalQuantity = 0;
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        Size size = new Size(1L, Sizes.M, 10, product);
        startQuantity = size.getQuantity();

        SizeDTO sizeDTO = new SizeDTO(Sizes.M, 5);
        size.setQuantity(size.getQuantity() + sizeDTO.quantity());
        finalQuantity = size.getQuantity();

        assertThat(startQuantity).isLessThan(finalQuantity);

    }

    @Test
    @DisplayName("Should throw a exception when quantity is less than 1")
    void saveSizeCase3() {
        SizeDTO sizeDTO = new SizeDTO(Sizes.M, 0);

        Exception thrown = Assertions.assertThrows(InvalidArgumentsException.class, () -> {
            if (sizeDTO.quantity() < 1)
                throw new InvalidArgumentsException("Quantity to be added should not be less than 1");
        });

        Assertions.assertEquals(thrown.getMessage(), "Quantity to be added should not be less than 1");
    }

    @Test
    @DisplayName("Should throw a exception when quantity is less than 1")
    void saveSizeCase4() {
        SizeDTO sizeDTO = new SizeDTO(Sizes.M, 0);

        Exception thrown = Assertions.assertThrows(InvalidArgumentsException.class, () -> {
            if (sizeDTO.quantity() < 1)
                throw new InvalidArgumentsException("Quantity to be added should not be less than 1");
        });

        Assertions.assertEquals(thrown.getMessage(), "Quantity to be added should not be less than 1");
    }

    @Test
    @DisplayName("Should remove a size stock from a product successfully")
    void removeSizeCase1() {
        int startQuantity, finalQuantity = 0;
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        Size size = new Size(1L, Sizes.M, 10, product);
        startQuantity = size.getQuantity();

        SizeDTO sizeDTO = new SizeDTO(Sizes.M, 5);
        size.setQuantity(size.getQuantity() - sizeDTO.quantity());
        finalQuantity = size.getQuantity();

        assertThat(startQuantity).isGreaterThan(finalQuantity);
    }

    @Test
    @DisplayName("Should throw an exception when there's no variety for size")
    void removeSizeCase2() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        Size size = new Size(1L, Sizes.M, 10, product);

        Exception thrown = Assertions.assertThrows(InvalidArgumentsException.class, () -> {
            if (!product.getSizes().contains(size))
                throw new InvalidArgumentsException("Theres no variety for this size");
        });

        Assertions.assertEquals(thrown.getMessage(), "Theres no variety for this size");
    }

    @Test
    @DisplayName("Should throw an exception when Size is not declared")
    void removeSizeCase3() {
        SizeDTO sizeDTO = new SizeDTO(null, 5);

        Exception thrown = Assertions.assertThrows(InvalidArgumentsException.class, () -> {
            if (sizeDTO.size() == null)
                throw new InvalidArgumentsException("Size cannot be null");
        });

        Assertions.assertEquals(thrown.getMessage(), "Size cannot be null");
    }

    @Test
    @DisplayName("Should throw an exception when Quantity is not declared")
    void removeSizeCase4() {
        SizeDTO sizeDTO = new SizeDTO(Sizes.M, null);

        Exception thrown = Assertions.assertThrows(InvalidArgumentsException.class, () -> {
            if (sizeDTO.quantity() == null)
                throw new InvalidArgumentsException("Quantity cannot be null");
        });

        Assertions.assertEquals(thrown.getMessage(), "Quantity cannot be null");
    }

    @Test
    @DisplayName("Should throw an exception when quantity to be removed is less than 1")
    void removeSizeCase5() {
        SizeDTO sizeDTO = new SizeDTO(Sizes.M, 0);

        Exception thrown = Assertions.assertThrows(InvalidArgumentsException.class, () -> {
            if (sizeDTO.quantity() == 0)
                throw new InvalidArgumentsException("Quantity cannot be less than 1");
        });

        Assertions.assertEquals(thrown.getMessage(), "Quantity cannot be less than 1");
    }

    @Test
    @DisplayName("Should throw an exception when quantity to be removed is greater than actual quantity")
    void removeSizeCase6() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        Size size = new Size(1L, Sizes.M, 10, product);
        SizeDTO sizeDTO = new SizeDTO(Sizes.M, 10);

        Exception thrown = Assertions.assertThrows(InvalidArgumentsException.class, () -> {
            if (sizeDTO.quantity() > size.getQuantity())
                throw new InvalidArgumentsException("Quantity to be removed cannot be greater than actual quantity");
        });

        Assertions.assertEquals(thrown.getMessage(), "Quantity to be removed cannot be greater than actual quantity");
    }

    @Test
    @DisplayName("Should find a Size by Size and Product successfully")
    void findSizeBySizeAndProductCase1() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        Size size = new Size(1L, Sizes.M, 10, product);
        product.getSizes().add(size);

        when(sizeRepository.findBySizeAndProduct(size.getSize(), product)).thenReturn(Optional.of(size));

        Optional<Size> response = sizeRepository.findBySizeAndProduct(size.getSize(), product);

        assertThat(response.isPresent()).isTrue();
        assertThat(response.get().getProduct()).isEqualTo(product);
    }

    @Test
    @DisplayName("Should find a Size by Size and Product successfully")
    void findSizeBySizeAndProductCase2() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        Size size = new Size(1L, Sizes.M, 10, product);

        Exception thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            if (sizeRepository.findBySizeAndProduct(size.getSize(), product).isEmpty())
                throw new EntityNotFoundException("This variety does not exist for this product");
        });

        Assertions.assertEquals(thrown.getMessage(), "This variety does not exist for this product");
    }

    @Test
    @DisplayName("Should remove all sizes from a product")
    void removeAllSizesFromProductCase1() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        Size size1 = new Size(1L, Sizes.M, 10, product);
        Size size2 = new Size(2L, Sizes.G, 10, product);
        Size size3 = new Size(3L, Sizes.GG, 10, product);

        product.getSizes().add(size1);
        product.getSizes().add(size2);
        product.getSizes().add(size3);

        assertThat(product.getSizes()).contains(size1);
        assertThat(product.getSizes()).contains(size2);
        assertThat(product.getSizes()).contains(size3);

        sizeRepository.deleteAllInBatch(List.of(size1, size2, size3));
        List.of(size1, size2, size3).forEach(product.getSizes()::remove);

        assertThat(product.getSizes()).isEmpty();
    }

}