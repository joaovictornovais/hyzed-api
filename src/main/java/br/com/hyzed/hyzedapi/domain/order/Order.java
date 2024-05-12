package br.com.hyzed.hyzedapi.domain.order;

import br.com.hyzed.hyzedapi.domain.item.Item;
import br.com.hyzed.hyzedapi.domain.item.ItemOrderDTO;
import br.com.hyzed.hyzedapi.domain.product.ProductDTO;
import br.com.hyzed.hyzedapi.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Instant date;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private BigDecimal total;

    @OneToMany(mappedBy = "order")
    Set<Item> items = new HashSet<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {
        this.orderStatus = OrderStatus.WAITING_PAYMENT;
        this.date = Instant.now().atZone(ZoneId.of("-03:00")).toLocalDateTime().toInstant(ZoneOffset.UTC);
    }

    public Order(User user) {
        this.orderStatus = OrderStatus.WAITING_PAYMENT;
        this.user = user;
        this.date = Instant.now().atZone(ZoneId.of("-03:00")).toLocalDateTime().toInstant(ZoneOffset.UTC);
    }

    public Set<ItemOrderDTO> getItems() {
        Set<ItemOrderDTO> items = new HashSet<>();

        for (Item item : this.items) {
            ProductDTO productDTO = new ProductDTO(item.getProduct().name(), item.getProduct().price());
            ItemOrderDTO temp = new ItemOrderDTO(productDTO, item.getQuantity(), item.getSubtotal(), item.getSize());
            items.add(temp);
        }

        return items;

    }

    public void setItems(Item item) {
        items.add(item);
    }
}
