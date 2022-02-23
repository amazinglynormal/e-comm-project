package sb.ecomm.order.dto;

import lombok.Getter;
import lombok.Setter;
import sb.ecomm.user.Address;

import java.util.List;

@Getter
@Setter
public class CreateOrderDTO {

    private List<Long> productIds;
    private Address shippingAddress;
    private String email;
    private String phone;

}
