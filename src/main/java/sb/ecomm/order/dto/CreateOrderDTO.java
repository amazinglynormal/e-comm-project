package sb.ecomm.order.dto;

import lombok.Getter;
import lombok.Setter;
import sb.ecomm.customer.Customer;
import sb.ecomm.order.OrderStatus;

import java.util.List;


@Getter
@Setter
public class CreateOrderDTO {
    private OrderStatus status;
    private Customer customer;
    private List<Long> productIds;
}
