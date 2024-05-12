package br.com.hyzed.hyzedapi.repositories;

import br.com.hyzed.hyzedapi.domain.user.RegisterRequestDTO;
import br.com.hyzed.hyzedapi.domain.user.User;
import br.com.hyzed.hyzedapi.domain.user.UserRole;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Should find a user by email successfully")
    void findByEmailCase1() {
        RegisterRequestDTO register = new RegisterRequestDTO(
                "Fulano", "da Silva", "fulano@gmail.com", "123456"
        );

        this.createUser(register);

        Optional<User> result = this.userRepository.findByEmail(register.email());

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not find a user by email")
    void findByEmailCase2() {
        Optional<User> result = this.userRepository.findByEmail("fulano@gmail.com");

        assertThat(result.isEmpty()).isTrue();
    }

    private User createUser(RegisterRequestDTO registerRequestDTO) {
        User user = new User(registerRequestDTO);
        user.setRole(UserRole.USER);
        this.entityManager.persist(user);
        return user;
    }

}