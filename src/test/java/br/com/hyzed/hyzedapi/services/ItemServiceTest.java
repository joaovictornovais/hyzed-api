package br.com.hyzed.hyzedapi.services;

import br.com.hyzed.hyzedapi.domain.item.Item;
import br.com.hyzed.hyzedapi.domain.order.Order;
import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.domain.size.Sizes;
import br.com.hyzed.hyzedapi.domain.user.RegisterRequestDTO;
import br.com.hyzed.hyzedapi.domain.user.User;
import br.com.hyzed.hyzedapi.domain.user.UserRole;
import br.com.hyzed.hyzedapi.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ItemServiceTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @InjectMocks
    ItemService itemService;

    @Mock
    ItemRepository itemRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should save a Item successfully")
    void saveItemCase1() {
        RegisterRequestDTO register = new
                RegisterRequestDTO("Fulano", "da Silva", "fulano@gmail.com", "123456");
        User user = new User(register);

        Product product = new Product(1L, "Corta vento", new BigDecimal("180"));
        Order order = new Order(user);

        Item item = new Item(product, order, 1, Sizes.M);
        itemRepository.save(item);

        verify(itemRepository, times(1)).save(item);
    }


}