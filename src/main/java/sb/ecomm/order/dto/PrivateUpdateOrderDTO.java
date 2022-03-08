package sb.ecomm.order.dto;

import sb.ecomm.order.OrderStatus;
import sb.ecomm.order.PaymentStatus;

public class PrivateUpdateOrderDTO {
    private double subtotal;
    private double shippingCost;
    private double totalCost;
    private PaymentStatus paymentStatus;
    private OrderStatus status;
}
