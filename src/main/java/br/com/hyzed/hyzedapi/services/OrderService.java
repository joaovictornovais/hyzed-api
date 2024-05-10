package br.com.hyzed.hyzedapi.services;

import br.com.hyzed.hyzedapi.domain.item.Item;
import br.com.hyzed.hyzedapi.domain.item.ItemDTO;
import br.com.hyzed.hyzedapi.domain.item.ProductsDTO;
import br.com.hyzed.hyzedapi.domain.order.Order;
import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.domain.size.Size;
import br.com.hyzed.hyzedapi.domain.size.SizeDTO;
import br.com.hyzed.hyzedapi.domain.size.Sizes;
import br.com.hyzed.hyzedapi.domain.user.User;
import br.com.hyzed.hyzedapi.exceptions.EntityNotFoundException;
import br.com.hyzed.hyzedapi.exceptions.InvalidArgumentsException;
import br.com.hyzed.hyzedapi.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final UserService userService;
    private final SizeService sizeService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository,
                        ItemService itemService,
                        UserService userService,
                        SizeService sizeService,
                        ProductService productService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
        this.userService = userService;
        this.sizeService = sizeService;
        this.productService = productService;
    }

    public Order findById(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    public Order create(String id, ProductsDTO productsDTO) {
        User user = userService.findUserById(id);
        Order order = new Order(user);

        for (ItemDTO item : productsDTO.products()) {

            Product product = productService.findById(item.productId());
            SizeDTO sizeDTO = new SizeDTO(item.size(), item.quantity());

            try {
                sizeService.removeSize(product, sizeDTO);
            } catch (InvalidArgumentsException e) {
                throw new InvalidArgumentsException("Unavailable stock for this product");
            }

            Item orderItem = itemService.createItem(order, product, item.quantity(), item.size());
            itemService.saveItem(orderItem);

            if (order.getTotal() == null) order.setTotal(orderItem.getSubtotal());
            else order.setTotal(order.getTotal().add(orderItem.getSubtotal()));

            order.setItems(orderItem);
        }
        orderRepository.save(order);
        return order;

    }

    public List<Order> getOrdersByUser(String id) {
        return orderRepository.findOrdersByUser(userService.findUserById(id));
    }

}
