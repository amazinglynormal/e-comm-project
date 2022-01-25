package sb.ecomm.user;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.apache.commons.text.RandomStringGenerator;
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
        String verificationHash = generateVerificationHash();
        user.setVerificationHash(verificationHash);
        User newUser = userRepository.save(user);
        return mapper.map(newUser, UserDTO.class);
    }

    UserDTO updateUserAccount(UUID id,
                                  UpdateUserDTO updateUserDTO) {
        User user =
                userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        updateUserUsername(user, updateUserDTO);
        updateUserEmail(user,updateUserDTO);
        updateUserPassword(user, updateUserDTO);
        updateUserAddress(user, updateUserDTO);

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

    private void updateUserUsername(User user,
                               UpdateUserDTO updateUserDTO) {
        if (updateUserDTO.getUsername() != null) {
            user.setUsername(updateUserDTO.getUsername());
        }
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

    private void updateUserAddress(User user, UpdateUserDTO updateUserDTO) {
        Address address = user.getAddress();
        if (address == null) {
            address = new Address();
        }

        if (updateUserDTO.getLine1() != null) {
            address.setLine1(updateUserDTO.getLine1());
        }
        if (updateUserDTO.getLine2() != null) {
            address.setLine2(updateUserDTO.getLine2());
        }
        if (updateUserDTO.getLine3() != null) {
            address.setLine3(updateUserDTO.getLine3());
        }
        if (updateUserDTO.getCity() != null) {
            address.setCity(updateUserDTO.getCity());
        }
        if (updateUserDTO.getProvince() != null) {
            address.setProvince(updateUserDTO.getProvince());
        }
        if (updateUserDTO.getCountry() != null) {
            address.setCountry(updateUserDTO.getCountry());
        }
        if (updateUserDTO.getZipCode() != null) {
            address.setZipCode(updateUserDTO.getZipCode());
        }

        user.setAddress(address);
    }

    private String generateVerificationHash() {
        UniformRandomProvider rng = RandomSource.MT.create();

        char[][] pairs = {{'0', '9'}, {'A', 'Z'}, {'a', 'z'}};

        RandomStringGenerator generator =
                new RandomStringGenerator.Builder().withinRange(pairs).usingRandom(rng::nextInt).build();

        return generator.generate(32);
    }
}
