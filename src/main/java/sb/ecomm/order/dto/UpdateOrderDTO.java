package sb.ecomm.order.dto;

import lombok.Getter;
import lombok.Setter;
import sb.ecomm.order.OrderStatus;

@Getter
@Setter
public class UpdateOrderDTO {

    private OrderStatus status;
    private Long addProduct;
    private Long removeProduct;
}
