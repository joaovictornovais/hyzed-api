package br.com.hyzed.hyzedapi.controllers;

import br.com.hyzed.hyzedapi.domain.order.Order;
import br.com.hyzed.hyzedapi.domain.order.OrderStatus;
import br.com.hyzed.hyzedapi.domain.order.OrderStatusDTO;
import br.com.hyzed.hyzedapi.services.OrderService;
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

    @PostMapping("/{id}/status")
    public ResponseEntity<Order> changeOrderStatus(@PathVariable String id, @RequestBody @Valid OrderStatusDTO orderStatus) {
        orderService.changeOrderStatus(id, orderStatus);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findById(id));
    }


}
