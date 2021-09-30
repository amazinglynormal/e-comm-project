package sb.ecomm.order.dto;

import lombok.Getter;
import lombok.Setter;
import sb.ecomm.order.OrderStatus;

import java.util.List;

@Getter
@Setter
public class UpdateOrderDTO {

    private Long id;
    private OrderStatus status;
    private Long customerId;
    private List<Long> productIds;
}