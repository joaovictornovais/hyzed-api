package br.com.hyzed.hyzedapi.domain.product;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private BigDecimal price;

    @JsonManagedReference
    @OneToMany(mappedBy = "product")
    Set<Image> images = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "product")
    Set<Size> sizes = new HashSet<>();

    public Product(ProductDTO data) {
        BeanUtils.copyProperties(data, this);
    }

}