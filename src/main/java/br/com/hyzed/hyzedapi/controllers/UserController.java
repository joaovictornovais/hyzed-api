package br.com.hyzed.hyzedapi.controllers;

import br.com.hyzed.hyzedapi.domain.item.ItemDTO;
import br.com.hyzed.hyzedapi.domain.item.ProductsDTO;
import br.com.hyzed.hyzedapi.domain.order.Order;
import br.com.hyzed.hyzedapi.domain.user.User;
import br.com.hyzed.hyzedapi.domain.user.UserMinDTO;
import br.com.hyzed.hyzedapi.services.OrderService;
import br.com.hyzed.hyzedapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @Operation(description = "Retorna informações minimas do usuario logado")
    @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso")
    @GetMapping
    public ResponseEntity<UserMinDTO> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userDetails));
    }

    @Operation(description = "Retorna as informações minimas de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserMinDTO> getUserMinInfo(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserMinInfo(id));
    }

    @Operation(description = "Retorna todas as ordens de pedidos de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        }
    )
    @GetMapping("/{id}/orders")
    public ResponseEntity<List<Order>> getOrders(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByUser(id));
    }

}
