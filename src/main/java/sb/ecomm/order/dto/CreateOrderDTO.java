package sb.ecomm.order.dto;

import lombok.Getter;
import lombok.Setter;
import sb.ecomm.order.OrderStatus;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class CreateOrderDTO {

    private OrderStatus status;
    private UUID userId;
    private List<Long> productIds;
}
