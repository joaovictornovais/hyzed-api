package br.com.hyzed.hyzedapi.services;

import br.com.hyzed.hyzedapi.domain.item.Item;
import br.com.hyzed.hyzedapi.domain.item.ItemDTO;
import br.com.hyzed.hyzedapi.domain.order.Order;
import br.com.hyzed.hyzedapi.domain.user.User;
import br.com.hyzed.hyzedapi.exceptions.EntityNotFoundException;
import br.com.hyzed.hyzedapi.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, ItemService itemService, UserService userService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
        this.userService = userService;
    }

    public Order findById(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    public Order create(String id, ItemDTO itemDTO) {
        User user = userService.findUserById(id);

        Order order = new Order();
        order.setUser(user);

        Item item = itemService.createItem(order, itemDTO);
        order.setTotal(item.getSubtotal());
        order = orderRepository.save(order);
        itemService.saveItem(item);
        order.getItems().add(item);
        return order;
    }

    public List<Order> getOrdersByUser(String id) {
        return orderRepository.findOrdersByUser(userService.findUserById(id));
    }

}
