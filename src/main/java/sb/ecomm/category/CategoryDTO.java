package sb.ecomm.category;

import lombok.Getter;
import lombok.Setter;
import sb.ecomm.product.Product;

import java.util.Set;

@Getter
@Setter
public class CategoryDTO {

    private Long id;

    private String name;

    private String description;

    private Set<Product> products;
}
