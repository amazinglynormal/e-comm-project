package sb.ecomm.customer;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService customerService;
}
