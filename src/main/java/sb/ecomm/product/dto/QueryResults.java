package sb.ecomm.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QueryResults {

    private List<ProductDTO> products;
    private int totalProducts;
    private int totalPages;

    public QueryResults(List<ProductDTO> products, int totalProducts,
                  int totalPages) {
        this.products = products;
        this.totalProducts = totalProducts;
        this.totalPages = totalPages;
    }
}
