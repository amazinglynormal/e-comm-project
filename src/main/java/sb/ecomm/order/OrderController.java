package sb.ecomm.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrderController {

    private final OrderService orderService;
}
