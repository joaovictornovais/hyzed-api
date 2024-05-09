package br.com.hyzed.hyzedapi.domain.order;

import br.com.hyzed.hyzedapi.domain.item.Item;
import br.com.hyzed.hyzedapi.domain.item.pk.ItemPK;
import br.com.hyzed.hyzedapi.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
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

    @OneToMany(mappedBy = "id.order")
    Set<Item> items = new HashSet<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {
        this.orderStatus = OrderStatus.WAITING_PAYMENT;
        this.date = Instant.now();
    }
}
