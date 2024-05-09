package br.com.hyzed.hyzedapi.domain.item;

import br.com.hyzed.hyzedapi.domain.item.pk.ItemPK;
import br.com.hyzed.hyzedapi.domain.order.Order;
import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.domain.product.ProductDTO;
import br.com.hyzed.hyzedapi.domain.size.Sizes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    @Enumerated(EnumType.STRING)
    private Sizes size;

    public Item(Product product, Order order, Integer quantity, Sizes size) {
        setProduct(product);
        setOrder(order);
        this.quantity = quantity;
        this.subtotal = id.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity));
        this.size = size;
    }

    public ProductDTO getProduct() {
        Product product = id.getProduct();
        return new ProductDTO(product.getName(), product.getPrice());
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
