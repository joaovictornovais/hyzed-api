package br.com.hyzed.hyzedapi.domain.item;

import br.com.hyzed.hyzedapi.domain.item.pk.ItemPK;
import br.com.hyzed.hyzedapi.domain.order.Order;
import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.domain.product.ProductDTO;
import br.com.hyzed.hyzedapi.domain.size.Sizes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(cascade = CascadeType.PERSIST) // avoid "save the transient instance before flushing"
    @JoinColumn(name = "order_id")
    private Order order;
    private Integer quantity;
    private BigDecimal subtotal;
    @Enumerated(EnumType.STRING)
    private Sizes size;

    public Item(Product product, Order order, Integer quantity, Sizes size) {
        setProduct(product);
        setOrder(order);
        this.quantity = quantity;
        this.subtotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        this.size = size;
    }

    public ProductDTO getProduct() {
        return new ProductDTO(product.getName(), product.getPrice());
    }

    @JsonIgnore
    public Order getOrder() {
        return order;
    }

}
