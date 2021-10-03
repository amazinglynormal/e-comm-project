package sb.ecomm.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sb.ecomm.customer.dto.CreateCustomerDTO;
import sb.ecomm.customer.dto.CustomerDTO;
import sb.ecomm.customer.dto.UpdateCustomerDTO;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    CustomerDTO findCustomerById(@PathVariable long id) {
        return customerService.findCustomerById(id);
    }

    @PostMapping
    CustomerDTO createNewCustomerAccount(@RequestBody CreateCustomerDTO createCustomerDTO) {
        return customerService.createNewCustomerAccount(createCustomerDTO);
    }

    @PutMapping("/{id}")
    CustomerDTO updateCustomerAccount(@PathVariable long id,
                                      @RequestBody UpdateCustomerDTO updateCustomerDTO) {
        return customerService.updateCustomerAccount(id, updateCustomerDTO);
    }

    @DeleteMapping("/{id}")
    void deleteCustomerAccount(@PathVariable long id) {
        customerService.deleteCustomerAccount(id);
    }
}
