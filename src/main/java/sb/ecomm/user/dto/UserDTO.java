package sb.ecomm.user.dto;

import lombok.Getter;
import lombok.Setter;
import sb.ecomm.order.dto.OrderDTO;
import sb.ecomm.user.Address;
import sb.ecomm.user.Role;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserDTO {

    private UUID id;
    private String username;
    private String email;
    private Role role;
    private Set<OrderDTO> orders;
    private Address address;
}
