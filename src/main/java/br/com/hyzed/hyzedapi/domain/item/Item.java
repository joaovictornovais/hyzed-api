package br.com.hyzed.hyzedapi.domain.item;

import br.com.hyzed.hyzedapi.domain.item.pk.ItemPK;
import br.com.hyzed.hyzedapi.domain.order.Order;
import br.com.hyzed.hyzedapi.domain.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Item {

    @JsonIgnore
    @EmbeddedId
    private ItemPK id = new ItemPK();
    private Integer quantity;
    private BigDecimal subtotal;

    public Item(Product product, Order order, Integer quantity) {
        setProduct(product);
        setOrder(order);
        this.quantity = quantity;
        this.subtotal = getProduct().getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public Product getProduct() {
        return id.getProduct();
    }

    public void setProduct(Product product) {
        id.setProduct(product);
    }

    @JsonIgnore
    public Order getOrder() {
        return id.getOrder();
    }

    public void setOrder(Order order) {
        id.setOrder(order);
    }

}
