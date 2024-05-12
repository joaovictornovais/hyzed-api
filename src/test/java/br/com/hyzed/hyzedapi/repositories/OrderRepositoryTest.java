package br.com.hyzed.hyzedapi.repositories;

import br.com.hyzed.hyzedapi.domain.order.Order;
import br.com.hyzed.hyzedapi.domain.user.RegisterRequestDTO;
import br.com.hyzed.hyzedapi.domain.user.User;
import br.com.hyzed.hyzedapi.domain.user.UserRole;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Should return all orders from a user successfully")
    void findOrdersByUserCase1() {
        RegisterRequestDTO register = new RegisterRequestDTO(
                "Fulano", "da Silva", "fulano@gmail.com", "123456"
        );

        User user = this.createUser(register);

        Order order1 = new Order();
        Order order2 = new Order();

        this.createOrder(order1, user);
        this.createOrder(order2, user);

        List<Order> result = orderRepository.findOrdersByUser(user);

        assertThat(result.size()).isEqualTo(2);

    }

    private User createUser(RegisterRequestDTO registerRequestDTO) {
        User user = new User(registerRequestDTO);
        user.setRole(UserRole.USER);
        this.entityManager.persist(user);
        return user;
    }

    private Order createOrder(Order order, User user) {
        order.setUser(user);
        this.entityManager.persist(order);
        return order;
    }

}