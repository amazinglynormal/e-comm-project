package sb.ecomm.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sb.ecomm.exceptions.UserNotFoundException;
import sb.ecomm.order.OrderService;
import sb.ecomm.order.dto.CreateOrderDTO;
import sb.ecomm.order.dto.OrderDTO;
import sb.ecomm.order.dto.UpdateOrderDTO;
import sb.ecomm.user.dto.CreateUserDTO;
import sb.ecomm.user.dto.UserDTO;
import sb.ecomm.user.dto.UpdateUserDTO;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       OrderService orderService,
                       PasswordEncoder passwordEncoder,
                       ModelMapper mapper) {
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    UserDTO findUserById(UUID id) {
        User user =
                userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        return mapper.map(user, UserDTO.class);
    }

    UserDTO createNewUserAccount(CreateUserDTO createUserDTO) {
        User user = mapper.map(createUserDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER);
        User newUser = userRepository.save(user);
        return mapper.map(newUser, UserDTO.class);
    }

    UserDTO updateUserAccount(UUID id,
                                  UpdateUserDTO updateUserDTO) {
        User user =
                userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        updateUserEmail(user,updateUserDTO);
        updateUserPassword(user, updateUserDTO);

        User savedUser = userRepository.save(user);

        return mapper.map(savedUser, UserDTO.class);
    }

    void deleteUserAccount(UUID id) {
        userRepository.deleteById(id);
    }

    OrderDTO findUserOrderById(UUID userId,
                               Long orderId) {
        OrderDTO order = orderService.findOrderById(orderId);
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("forbidden yeah");
        }
        return order;
    }

    OrderDTO addNewUserOrder(CreateOrderDTO createOrderDTO, UUID id) {
         return orderService.addNewOrder(createOrderDTO, id);
    }

    OrderDTO updateUserOrder(UUID userId,
                             Long orderId,
                             UpdateOrderDTO updateOrderDTO) {
        OrderDTO order = orderService.findOrderById(orderId);
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("forbidden yeah");
        }
        return orderService.updateOrder(orderId, updateOrderDTO);
    }

    void deleteUserOrder(UUID userId, Long orderId) {
        OrderDTO order = orderService.findOrderById(orderId);
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("forbidden yeah");
        }

        orderService.deleteOrderById(orderId);
    }

    private void updateUserEmail(User user,
                                     UpdateUserDTO updateUserDTO) {
        if (updateUserDTO.getEmail() != null) {
            user.setEmail(updateUserDTO.getEmail());
        }
    }

    private void updateUserPassword(User user,
                                        UpdateUserDTO updateUserDTO) {
        if (updateUserDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
        }
    }
}
