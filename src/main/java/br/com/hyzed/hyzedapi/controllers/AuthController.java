package br.com.hyzed.hyzedapi.controllers;

import br.com.hyzed.hyzedapi.domain.user.LoginRequestDTO;
import br.com.hyzed.hyzedapi.domain.user.ResponseDTO;
import br.com.hyzed.hyzedapi.domain.user.RegisterRequestDTO;
import br.com.hyzed.hyzedapi.domain.user.User;
import br.com.hyzed.hyzedapi.exceptions.EntityNotFoundException;
import br.com.hyzed.hyzedapi.exceptions.InvalidArgumentsException;
import br.com.hyzed.hyzedapi.infra.security.TokenService;
import br.com.hyzed.hyzedapi.repositories.UserRepository;
import br.com.hyzed.hyzedapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserService userService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        User user = userRepository.findByEmail(data.email()).orElseThrow(() ->  new EntityNotFoundException("User not found"));
        if (passwordEncoder.matches(data.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(user.getFirstName(), token));
        }
        throw new InvalidArgumentsException("Invalid credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody @Valid RegisterRequestDTO data) {
        User user = new User(data);
        user.setPassword(passwordEncoder.encode(data.password()));
        userService.registerUser(user);
        String token = tokenService.generateToken(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(user.getFirstName(), token));
    }

}
