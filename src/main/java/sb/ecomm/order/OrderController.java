package sb.ecomm.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sb.ecomm.order.dto.CreateOrderDTO;
import sb.ecomm.order.dto.OrderDTO;
import sb.ecomm.order.dto.UpdateOrderDTO;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    OrderDTO getOrderById(@PathVariable long id) {
        return orderService.findOrderById(id);
    }

    @PostMapping
    OrderDTO addNewOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        return orderService.addNewOrder(createOrderDTO);
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
