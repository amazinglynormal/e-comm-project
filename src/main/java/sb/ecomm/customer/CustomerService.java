package sb.ecomm.customer;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sb.ecomm.customer.dto.CreateCustomerDTO;
import sb.ecomm.customer.dto.CustomerDTO;
import sb.ecomm.customer.dto.UpdateCustomerDTO;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private ModelMapper mapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           PasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    CustomerDTO findCustomerById(long id) {
        Customer customer =
                customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));

        return mapper.map(customer, CustomerDTO.class);
    }

    CustomerDTO createNewCustomerAccount(CreateCustomerDTO createCustomerDTO) {
        Customer customer = mapper.map(createCustomerDTO, Customer.class);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Customer newCustomer = customerRepository.save(customer);
        return mapper.map(newCustomer, CustomerDTO.class);
    }

    CustomerDTO updateCustomerAccount(long id,
                                      UpdateCustomerDTO updateCustomerDTO) {
        Customer customer =
                customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        updateCustomerEmail(customer,updateCustomerDTO);
        updateCustomerPassword(customer, updateCustomerDTO);

        Customer savedCustomer = customerRepository.save(customer);

        return mapper.map(savedCustomer, CustomerDTO.class);
    }

    void deleteCustomerAccount(long id) {
        customerRepository.deleteById(id);
    }

    private void updateCustomerEmail(Customer customer,
                              UpdateCustomerDTO updateCustomerDTO) {
        if (updateCustomerDTO.getEmail() != null) {
            customer.setEmail(updateCustomerDTO.getEmail());
        }
    }

    private void updateCustomerPassword(Customer customer,
                                 UpdateCustomerDTO updateCustomerDTO) {
        if (updateCustomerDTO.getPassword() != null) {
            customer.setPassword(passwordEncoder.encode(updateCustomerDTO.getPassword()));
        }
    }
}
