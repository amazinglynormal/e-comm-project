package sb.ecomm.user.dto;

import lombok.Getter;
import lombok.Setter;
import sb.ecomm.order.dto.OrderDTO;

import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String email;
    private Set<OrderDTO> orders;
}
