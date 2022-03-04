package sb.ecomm.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateCheckoutSessionResponse {
    private String sessionId;
    private OrderDTO order;
}
