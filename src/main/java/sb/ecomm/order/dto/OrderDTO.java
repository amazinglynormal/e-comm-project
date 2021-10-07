package sb.ecomm.order.dto;

import lombok.Getter;
import lombok.Setter;
import sb.ecomm.order.OrderStatus;
import sb.ecomm.product.Product;

import java.util.List;

@Getter
@Setter
public class OrderDTO {

    private Long id;
    private OrderStatus status;
    private Long userId;
    private List<Product> products;
}
