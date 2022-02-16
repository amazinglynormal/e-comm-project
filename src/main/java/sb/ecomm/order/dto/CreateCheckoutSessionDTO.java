package sb.ecomm.order.dto;

import lombok.Getter;
import lombok.Setter;
import sb.ecomm.order.Currency;

import java.util.List;

@Getter
@Setter
public class CreateCheckoutSessionDTO {

    private String email;
    private List<Long> productIds;
    private Currency currency;
}
