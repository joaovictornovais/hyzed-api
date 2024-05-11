package br.com.hyzed.hyzedapi.services;

import br.com.hyzed.hyzedapi.domain.user.User;
import br.com.hyzed.hyzedapi.domain.user.UserMinDTO;
import br.com.hyzed.hyzedapi.exceptions.EntityNotFoundException;
import br.com.hyzed.hyzedapi.exceptions.InvalidArgumentsException;
import br.com.hyzed.hyzedapi.repositories.UserRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if (findByEmail(user.getEmail()).isPresent()) {
            throw new InvalidArgumentsException("E-mail already registered");
        }
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @PostAuthorize("returnObject.email() == authentication.name")
    public UserMinDTO getUserMinInfo(String id) {
        User user = findUserById(id);
        return new UserMinDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

}
