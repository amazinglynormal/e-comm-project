package sb.ecomm.order.dto;

import lombok.Getter;
import lombok.Setter;
import sb.ecomm.order.Currency;
import sb.ecomm.user.Address;

import java.util.List;

@Getter
@Setter
public class CreateCheckoutSessionDTO {

    private String email;
    private String name;
    private List<Long> productIds;
    private Currency currency;
    private Address shippingAddress;
    private String phone;

}
