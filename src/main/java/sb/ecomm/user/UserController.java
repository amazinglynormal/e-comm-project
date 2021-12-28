package sb.ecomm.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sb.ecomm.order.dto.CreateOrderDTO;
import sb.ecomm.order.dto.OrderDTO;
import sb.ecomm.order.dto.UpdateOrderDTO;
import sb.ecomm.user.dto.CreateUserDTO;
import sb.ecomm.user.dto.UserDTO;
import sb.ecomm.user.dto.UpdateUserDTO;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    UserDTO findUserById(@PathVariable UUID userId) {
        return userService.findUserById(userId);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    OrderDTO findUserOrderById(@PathVariable UUID userId,
                               @PathVariable long orderId) {
        return userService.findUserOrderById(userId, orderId);
    }

    @PostMapping("/{userId}/orders")
    OrderDTO addNewUserOrder(@RequestBody CreateOrderDTO createOrderDTO,
                             @PathVariable UUID userId) {
        return userService.addNewUserOrder(createOrderDTO, userId);
    }

    @PutMapping("/{userId}/orders/{orderId}")
    OrderDTO updateUserOrder(@PathVariable UUID userId,
                             @PathVariable long orderId,
                             @RequestBody UpdateOrderDTO updateOrderDTO) {
        return userService.updateUserOrder(userId, orderId, updateOrderDTO);
    }

    @DeleteMapping("/{userId}/orders/{orderId}")
    void deleteUserOrder(@PathVariable UUID userId,
                         @PathVariable long orderId) {
        userService.deleteUserOrder(userId, orderId);
    }

    @PostMapping
    UserDTO createNewUserAccount(@RequestBody CreateUserDTO createUserDTO) {
        return userService.createNewUserAccount(createUserDTO);
    }

    @PutMapping("/{userId}")
    UserDTO updateUserAccount(@PathVariable UUID userId,
                              @RequestBody UpdateUserDTO updateUserDTO) {
        return userService.updateUserAccount(userId, updateUserDTO);
    }

    @DeleteMapping("/{userId}")
    void deleteUserAccount(@PathVariable UUID userId) {
        userService.deleteUserAccount(userId);
    }
}
