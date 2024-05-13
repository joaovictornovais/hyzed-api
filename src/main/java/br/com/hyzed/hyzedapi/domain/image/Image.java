package br.com.hyzed.hyzedapi.domain.image;

import br.com.hyzed.hyzedapi.domain.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name = "product_images")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String url;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Image(ImageDTO data) {
        BeanUtils.copyProperties(data, this);
    }

    public Image(Long id, String url) {
        this.id = id;
        this.url = url;
    }


}
