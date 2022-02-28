package sb.ecomm.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sb.ecomm.order.dto.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    CreateCheckoutSessionResponse createCheckoutSession(@RequestBody CreateCheckoutSessionDTO createCheckoutSessionDTO) {
        return orderService.createCheckoutSession(createCheckoutSessionDTO);
    }

    @PostMapping("/webhook")
    void stripeWebhookEndpoint(@RequestBody String jsonEvent, @RequestHeader(name = "stripe-signature") String stripeSignature) {
        orderService.processWebhookEvent(jsonEvent, stripeSignature);
    }

    @GetMapping("/{id}")
    OrderDTO getOrderById(@PathVariable long id) {
        return orderService.findOrderById(id);
    }

    @PutMapping("/{id}")
    OrderDTO updateOrder(@PathVariable long id,
                         @RequestBody UpdateOrderDTO updateOrderDTO) {
        return orderService.updateOrder(id, updateOrderDTO);
    }

    @DeleteMapping("/{id}")
    void deleteOrderById(@PathVariable long id) {
        orderService.deleteOrderById(id);
    }
}
