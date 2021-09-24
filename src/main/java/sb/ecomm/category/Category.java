package sb.ecomm.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sb.ecomm.product.Product;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Category")
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;
}
