package br.com.hyzed.hyzedapi.domain.item.pk;

import br.com.hyzed.hyzedapi.domain.order.Order;
import br.com.hyzed.hyzedapi.domain.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ItemPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
