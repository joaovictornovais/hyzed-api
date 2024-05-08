package br.com.hyzed.hyzedapi.controllers;

import br.com.hyzed.hyzedapi.domain.user.User;
import br.com.hyzed.hyzedapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(id));
    }

}
