package br.com.hyzed.hyzedapi.services;

import br.com.hyzed.hyzedapi.domain.item.Item;
import br.com.hyzed.hyzedapi.domain.item.ItemDTO;
import br.com.hyzed.hyzedapi.domain.item.ProductsDTO;
import br.com.hyzed.hyzedapi.domain.order.Order;
import br.com.hyzed.hyzedapi.domain.order.OrderStatus;
import br.com.hyzed.hyzedapi.domain.order.OrderStatusDTO;
import br.com.hyzed.hyzedapi.domain.order.PaymentResponseDTO;
import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.domain.size.Size;
import br.com.hyzed.hyzedapi.domain.size.SizeDTO;
import br.com.hyzed.hyzedapi.domain.size.Sizes;
import br.com.hyzed.hyzedapi.domain.user.User;
import br.com.hyzed.hyzedapi.exceptions.EntityNotFoundException;
import br.com.hyzed.hyzedapi.exceptions.InvalidArgumentsException;
import br.com.hyzed.hyzedapi.exceptions.PaymentRequiredException;
import br.com.hyzed.hyzedapi.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
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

        if (productsDTO.products().isEmpty())
            throw new InvalidArgumentsException("Products list should not be blank");

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

    public void changeOrderStatus(String id, OrderStatusDTO dto) {
        Order order = findById(id);
        if (dto.orderStatus() == OrderStatus.WAITING_PAYMENT) setStatusWaitingPayment(order);
        else if (dto.orderStatus() == OrderStatus.PAID) setStatusPaid(order);
        else if (dto.orderStatus() == OrderStatus.SHIPPED) setStatusShipped(order);
        else if (dto.orderStatus() == OrderStatus.DELIVERED) setStatusDelivered(order);
        else if (dto.orderStatus() == OrderStatus.CANCELED) setStatusCanceled(order);
    }

    private void setOrderStatus(Order order, OrderStatus orderStatus) {
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
    }

    private void setStatusWaitingPayment(Order order) {
        if (order.getOrderStatus() != null)
            throw new InvalidArgumentsException(
                    "This order cannot have this status."
            );
        setOrderStatus(order, OrderStatus.WAITING_PAYMENT);
    }

    private void setStatusPaid(Order order) {
        if (order.getOrderStatus() != OrderStatus.WAITING_PAYMENT)
            throw new InvalidArgumentsException("This order cannot have this status.");

        String url = "https://run.mocky.io/v3/7cfae6fd-eb87-4a48-9516-35dd9993aa15";
        RestTemplate restTemplate = new RestTemplate();
        boolean response = Objects.requireNonNull(restTemplate.getForObject(url, PaymentResponseDTO.class)).approved();
        if (!response) throw new PaymentRequiredException("Error while processing payment");

        setOrderStatus(order, OrderStatus.PAID);
    }

    private void setStatusShipped(Order order) {
        if (order.getOrderStatus() != OrderStatus.PAID)
            throw new InvalidArgumentsException("This order cannot have this status.");
        setOrderStatus(order, OrderStatus.SHIPPED);
    }

    private void setStatusDelivered(Order order) {
        if (order.getOrderStatus() != OrderStatus.SHIPPED)
            throw new InvalidArgumentsException("This order cannot have this status.");
        setOrderStatus(order, OrderStatus.DELIVERED);
    }

    private void setStatusCanceled(Order order) {
        if (order.getOrderStatus() == OrderStatus.DELIVERED)
            throw new InvalidArgumentsException("This order cannot have this status.");

        if (order.getOrderStatus() != OrderStatus.WAITING_PAYMENT)
            // SEND EMAIL TO USER
            System.out.println("R$" + order.getTotal() + " will be refunded");
        setOrderStatus(order, OrderStatus.CANCELED);
    }

}
