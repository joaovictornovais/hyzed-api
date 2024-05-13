package br.com.hyzed.hyzedapi.services;

import br.com.hyzed.hyzedapi.domain.item.Item;
import br.com.hyzed.hyzedapi.domain.item.ItemDTO;
import br.com.hyzed.hyzedapi.domain.item.ProductsDTO;
import br.com.hyzed.hyzedapi.domain.order.Order;
import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.domain.size.Size;
import br.com.hyzed.hyzedapi.domain.size.SizeDTO;
import br.com.hyzed.hyzedapi.domain.size.Sizes;
import br.com.hyzed.hyzedapi.domain.user.RegisterRequestDTO;
import br.com.hyzed.hyzedapi.domain.user.User;
import br.com.hyzed.hyzedapi.exceptions.EntityNotFoundException;
import br.com.hyzed.hyzedapi.exceptions.InvalidArgumentsException;
import br.com.hyzed.hyzedapi.repositories.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    SizeService sizeService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should find a order by id successfully")
    void findOrderByIdCase1() {
        String id = UUID.randomUUID().toString();
        Order order = new Order();
        order.setId(id);

        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        Optional<Order> response = orderRepository.findById(id);

        assertThat(response.isPresent()).isTrue();
        assertThat(response.get().getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Should throw a exception when order not found by id")
    void findOrderByIdCase2() {
        String id = UUID.randomUUID().toString();

        Exception thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            if (orderRepository.findById(id).isEmpty())
                throw new EntityNotFoundException("Order not found");
        });

        Assertions.assertEquals(thrown.getMessage(), "Order not found");
    }

    @Test
    @DisplayName("Should create a order successfully")
    void createOrderCase1() {
        RegisterRequestDTO register = new
                RegisterRequestDTO("Fulano", "da Silva", "fulano@gmail.com", "123456");
        User user = new User(register);
        Order order = new Order(user);

        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        Item item = new Item(product, order, 1, Sizes.M);

        order.setItems(item);
        orderRepository.save(order);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    @DisplayName("Should throw a exception when given products list is null")
    void createOrderCase2() {
        Set<ItemDTO> items = new HashSet<>();
        ProductsDTO productsDTO = new ProductsDTO(items);

        Exception thrown = Assertions.assertThrows(InvalidArgumentsException.class, () -> {
            if (productsDTO.products().isEmpty()) {
                throw new InvalidArgumentsException("Products list is empty");
            }
        });

        Assertions.assertEquals(thrown.getMessage(), "Products list is empty");
    }

    @Test
    @DisplayName("Should throw a exception when theres no stock available")
    void createOrderCase3() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        SizeDTO sizeDTO = new SizeDTO(Sizes.M, 1);

        Optional<Size> response = sizeService.findBySizeAndProduct(sizeDTO.size(), product);

        Exception thrown = Assertions.assertThrows(InvalidArgumentsException.class, () -> {
            if (response.isEmpty())
                throw new InvalidArgumentsException("There is no stock available");
        });

        Assertions.assertEquals(thrown.getMessage(), "There is no stock available");
    }

    @Test
    @DisplayName("Should throw a exception when ")
    void createOrderCase4() {
        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        SizeDTO sizeDTO = new SizeDTO(Sizes.M, 1);

        Optional<Size> response = sizeService.findBySizeAndProduct(sizeDTO.size(), product);

        Exception thrown = Assertions.assertThrows(InvalidArgumentsException.class, () -> {
            if (response.isEmpty())
                throw new InvalidArgumentsException("There is no stock available");
        });

        Assertions.assertEquals(thrown.getMessage(), "There is no stock available");
    }

}