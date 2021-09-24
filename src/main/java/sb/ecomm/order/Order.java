package sb.ecomm.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sb.ecomm.product.Product;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Order")
@Table(name = "ORDER")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany
    private List<Product> products;

}
