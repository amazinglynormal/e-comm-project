package sb.ecomm.order.dto;

import lombok.Getter;
import lombok.Setter;
import sb.ecomm.order.OrderStatus;
import sb.ecomm.user.Address;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class CreateOrderDTO {

    private OrderStatus status;
    private List<Long> productIds;
    private Address shippingAddress;
    private String email;
    private String phone;

}
