package sb.ecomm.order.dto;

import lombok.Getter;
import lombok.Setter;
import sb.ecomm.order.OrderStatus;
import sb.ecomm.order.PaymentStatus;
import sb.ecomm.user.Address;

import java.util.UUID;

@Getter
@Setter
public class UpdateOrderDTO {

    private UUID userId;
    private OrderStatus status;
    private Long addProduct;
    private Long removeProduct;
    private Address shippingAddress;
    private String email;
    private String phone;
    private PaymentStatus paymentStatus;

}
