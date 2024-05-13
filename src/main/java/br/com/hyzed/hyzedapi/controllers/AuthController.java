package br.com.hyzed.hyzedapi.controllers;

import br.com.hyzed.hyzedapi.domain.user.*;
import br.com.hyzed.hyzedapi.exceptions.EntityNotFoundException;
import br.com.hyzed.hyzedapi.exceptions.InvalidArgumentsException;
import br.com.hyzed.hyzedapi.infra.security.TokenService;
import br.com.hyzed.hyzedapi.repositories.UserRepository;
import br.com.hyzed.hyzedapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

    @Operation(description = "Deve retornar um token do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o token com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou mal sucedida")
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        User user = userRepository.findByEmail(data.email()).orElseThrow(() ->  new EntityNotFoundException("User not found"));
        if (passwordEncoder.matches(data.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(user.getFirstName(), token));
        }
        throw new InvalidArgumentsException("Invalid credentials");
    }

    @Operation(description = "Deve registrar um usuário ao banco de dados e retornar um token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso e token retornado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody @Valid RegisterRequestDTO data) {
        User user = new User(data);
        user.setPassword(passwordEncoder.encode(data.password()));
        user.setRole(UserRole.USER);
        userService.registerUser(user);
        String token = tokenService.generateToken(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(user.getFirstName(), token));
    }

}
