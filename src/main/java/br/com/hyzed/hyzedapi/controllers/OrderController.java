package br.com.hyzed.hyzedapi.controllers;

import br.com.hyzed.hyzedapi.domain.order.Order;
import br.com.hyzed.hyzedapi.domain.order.OrderStatus;
import br.com.hyzed.hyzedapi.domain.order.OrderStatusDTO;
import br.com.hyzed.hyzedapi.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(description = "Atualiza o Status de um pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status de pedido alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> changeOrderStatus(@PathVariable String id, @RequestBody @Valid OrderStatusDTO orderStatus) {
        orderService.changeOrderStatus(id, orderStatus);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findById(id));
    }


}
