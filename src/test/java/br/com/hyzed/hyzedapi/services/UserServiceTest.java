package br.com.hyzed.hyzedapi.services;

import br.com.hyzed.hyzedapi.domain.user.User;
import br.com.hyzed.hyzedapi.domain.user.UserRole;
import br.com.hyzed.hyzedapi.exceptions.EntityNotFoundException;
import br.com.hyzed.hyzedapi.exceptions.InvalidArgumentsException;
import br.com.hyzed.hyzedapi.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @InjectMocks
    @Autowired
    UserService userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should find a user by email successfully")
    void findByEmailCase1() {
        User user = new User(
                UUID.randomUUID().toString(), "Fulano", "da Silva",
                "fulano@gmail.com", "123456", UserRole.USER
        );

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Optional<User> response = userRepository.findByEmail(user.getEmail());

        assertThat(response.isPresent()).isTrue();
        assertThat(response.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("Should throw a exception when user not found by email")
    void findByEmailCase2() {
        Exception thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            if (userRepository.findByEmail("fulano@gmail.com").isEmpty())
                throw new EntityNotFoundException("User not found");
        });

        Assertions.assertEquals(thrown.getMessage(), "User not found");
    }

    @Test
    @DisplayName("Should find a user by id successfully")
    void findByIdCase1() {
        User user = new User(
                UUID.randomUUID().toString(), "Fulano", "da Silva",
                "fulano@gmail.com", "123456", UserRole.USER
        );

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Optional<User> response = userRepository.findById(user.getId());

        assertThat(response.isPresent()).isTrue();
        assertThat(response.get().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("Should throw a exception when not find a user by id")
    void findByIdCase2() {
        Exception thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            if (userRepository.findById(UUID.randomUUID().toString()).isEmpty())
                throw new EntityNotFoundException("User not found");
        });

        Assertions.assertEquals(thrown.getMessage(), "User not found");
    }

    @Test
    @DisplayName("Should register a user successfully")
    void registerCase1() {
        User user = new User(
                UUID.randomUUID().toString(), "Fulano", "da Silva",
                "fulano@gmail.com", "123456", UserRole.USER
        );
        userRepository.save(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Should throw an exception when trying to register with a already registered email")
    void registerCase2() {
        User user = new User(
                UUID.randomUUID().toString(), "Fulano", "da Silva",
                "fulano@gmail.com", "123456", UserRole.USER
        );

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Exception thrown = Assertions.assertThrows(InvalidArgumentsException.class, () -> {
            if (userRepository.findByEmail(user.getEmail()).isPresent())
                throw new InvalidArgumentsException("Email already registered");
        });

        Assertions.assertEquals(thrown.getMessage(), "Email already registered");
    }

}