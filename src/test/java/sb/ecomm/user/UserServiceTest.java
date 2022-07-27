package sb.ecomm.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import sb.ecomm.email.EmailService;
import sb.ecomm.exceptions.OrderNotFoundException;
import sb.ecomm.exceptions.UserNotFoundException;
import sb.ecomm.order.Order;
import sb.ecomm.order.OrderService;
import sb.ecomm.order.OrderStatus;
import sb.ecomm.order.dto.CreateOrderDTO;
import sb.ecomm.order.dto.OrderDTO;
import sb.ecomm.order.dto.UpdateOrderDTO;
import sb.ecomm.product.Product;
import sb.ecomm.user.dto.CreateUserDTO;
import sb.ecomm.user.dto.UpdateUserDTO;
import sb.ecomm.user.dto.UserDTO;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock private UserRepository userRepository;
    @Mock private OrderService orderService;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private ModelMapper mapper;
    @Mock private EmailService emailService;

    @InjectMocks
    private UserService userService;


    @Test
    void findUserById_success() {
        UUID testUUID = UUID.randomUUID();
        User testUser = getSingleTestUser(testUUID);

        UserDTO testUserDTO = getSingleTestUserDTO(testUUID);

        when(userRepository.findById(testUUID)).thenReturn(Optional.of(testUser));
        when(mapper.map(testUser, UserDTO.class)).thenReturn(testUserDTO);

        UserDTO userDTO = userService.findUserById(testUUID);

        assertEquals(testUUID, userDTO.getId());
    }

    @Test
    void findUserById_failure() {
        UUID testUUID = UUID.randomUUID();
        when(userRepository.findById(testUUID)).thenThrow(new UserNotFoundException(testUUID));

        assertThrows(UserNotFoundException.class, () -> {
            userService.findUserById(testUUID);
        });
    }

    @Test
    void createNewUserAccount_success() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setUsername("username");
        createUserDTO.setEmail("email@test.com");
        createUserDTO.setPassword("abcde12345");

        UUID testUUID = UUID.randomUUID();

        User testUser = getSingleTestUser(testUUID);
        UserDTO userDTO = getSingleTestUserDTO(testUUID);

        when(mapper.map(createUserDTO, User.class)).thenReturn(testUser);
        when(passwordEncoder.encode(any(String.class))).thenReturn("12345abcde");
        when(userRepository.save(testUser)).thenReturn(testUser);
        when(mapper.map(testUser, UserDTO.class)).thenReturn(userDTO);

        UserDTO testUserDTO = userService.createNewUserAccount(createUserDTO);

        assertEquals(testUUID, testUserDTO.getId());
    }

    @Test
    void updateUserAccount_success() {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setUsername("updated-username");
        updateUserDTO.setEmail("updatedEmail@test.com");
        updateUserDTO.setLine1("updated-address-line1");
        updateUserDTO.setCity("updated-address-city");

        UUID testUUID = UUID.randomUUID();

        User testUser = getSingleTestUser(testUUID);
        UserDTO userDTO = getSingleTestUserDTO(testUUID);
        Address address = new Address();
        address.setLine1("updated-address-line1");
        address.setCity("updated-address-city");
        userDTO.setAddress(address);
        userDTO.setUsername("updated-username");
        userDTO.setEmail("updatedEmail@test.com");

        when(userRepository.findById(testUUID)).thenReturn(Optional.of(testUser));
        when(userRepository.save(testUser)).thenReturn(testUser);
        when(mapper.map(testUser, UserDTO.class)).thenReturn(userDTO);

        UserDTO testUserDTO = userService.updateUserAccount(testUUID, updateUserDTO);

        assertEquals(testUUID, testUserDTO.getId());
        assertEquals("updated-username", testUserDTO.getUsername());
        assertEquals("updatedEmail@test.com", testUserDTO.getEmail());
        assertEquals(address, testUserDTO.getAddress());

    }

    @Test
    void updateUserAccount_failure() {
        UUID testUUID = UUID.randomUUID();
        when(userRepository.findById(testUUID)).thenThrow(new UserNotFoundException(testUUID));

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setUsername("updated-username");
        updateUserDTO.setEmail("updatedEmail@test.com");
        updateUserDTO.setLine1("updated-address-line1");
        updateUserDTO.setCity("updated-address-city");

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUserAccount(testUUID, updateUserDTO);
        });
    }

    @Test
    void findUserOrderById_success() {
        UUID testUUID = UUID.randomUUID();
        Long testOrderId = 1L;

        OrderDTO testOrderDTO = getSingleOrderDTO(testOrderId, testUUID);

        when(orderService.findOrderById(testOrderId)).thenReturn(testOrderDTO);

        OrderDTO orderDTO = userService.findUserOrderById(testUUID, testOrderId);
        assertNotNull(orderDTO);
        assertEquals(testOrderId, orderDTO.getId());
        assertEquals(testUUID, orderDTO.getUserId());
    }

    @Test
    void findUserOrderByIdThrowsExceptionWhenGivenWrongOrderId() {
        Long wrongOrderId = 2L;

        when(orderService.findOrderById(wrongOrderId)).thenThrow(new OrderNotFoundException(wrongOrderId));

        assertThrows(OrderNotFoundException.class, () -> {
            userService.findUserOrderById(UUID.randomUUID(), wrongOrderId);
        });

    }

    @Test
    void findUserOrderByIdThrowsExceptionWhenUserIdDoesNotMatchUserIdInOrder() {
        UUID testUUID = UUID.randomUUID();
        Long testOrderId = 1L;
        UUID wrongUserId = UUID.randomUUID();

        OrderDTO testOrderDTO = getSingleOrderDTO(testOrderId, testUUID);

        when(orderService.findOrderById(testOrderId)).thenReturn(testOrderDTO);

        assertThrows(RuntimeException.class, () -> {
            userService.findUserOrderById(wrongUserId, testOrderId);
        }, "Not authorised to access this resource");
    }

    @Test
    void getAllOrdersPlacedByUser_success() {
        UUID testUUID = UUID.randomUUID();
        OrderDTO order1 = getSingleOrderDTO(1L, testUUID);
        OrderDTO order2 = getSingleOrderDTO(2L, testUUID);

        List<OrderDTO> orders = List.of(order1, order2);
        when(orderService.findAllOrdersPlacedByUser(testUUID)).thenReturn(orders);

        List<OrderDTO> userOrders = userService.getAllOrdersPlacedByUser(testUUID);

        assertEquals(2, userOrders.size());
    }

    @Test
    void getAllOrdersPlacedByUserReturnsEmptyListWhenUserHasNoOrders() {
        UUID testUUID = UUID.randomUUID();
        List<OrderDTO> orders = List.of();

        when(orderService.findAllOrdersPlacedByUser(testUUID)).thenReturn(orders);

        List<OrderDTO> userOrders = userService.getAllOrdersPlacedByUser(testUUID);

        assertEquals(0, userOrders.size());
    }

    @Test
    void updateUserOrderThrowsExceptionWhenGivenWrongOrderId() {
        UUID testUUID = UUID.randomUUID();
        Long orderId = 1L;
        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
        when(orderService.findOrderById(orderId)).thenThrow(new OrderNotFoundException(orderId));

        assertThrows(OrderNotFoundException.class, () -> {
            userService.updateUserOrder(testUUID, orderId, updateOrderDTO);
        });
    }

    @Test
    void updateUserOrderThrowsExceptionWhenUserIdDoesNotMatchUserIdInOrder() {
        UUID testUUID = UUID.randomUUID();
        Long orderId = 1L;
        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
        OrderDTO orderDTO = getSingleOrderDTO(1L, UUID.randomUUID());
        when(orderService.findOrderById(orderId)).thenReturn(orderDTO);


        assertThrows(RuntimeException.class, () -> {
            userService.updateUserOrder(testUUID, orderId, updateOrderDTO);
        }, "Not authorised to access this resource");
    }

    @Test
    void createCheckoutSession() {
    }

    @Test
    void deleteUserOrderThrowsExceptionWhenUserIdDoesNotMatchUserIdInOrder() {
        UUID testUUID = UUID.randomUUID();
        Long orderId = 1L;
        OrderDTO orderDTO = getSingleOrderDTO(1L, UUID.randomUUID());
        when(orderService.findOrderById(orderId)).thenReturn(orderDTO);

        assertThrows(RuntimeException.class, () -> {
            userService.deleteUserOrder(testUUID, orderId);
        }, "Not authorised to modify this order");
    }


    private User getSingleTestUser(UUID testUUID) {
        Set<Order> orders = Collections.emptySet();
        User testUser = new User("username", "email@test.com", "password123", Role.CUSTOMER, "abcde12345", orders);
        testUser.setId(testUUID);
        return testUser;
    }

    private UserDTO getSingleTestUserDTO(UUID testUUID) {
        UserDTO testUserDTO = new UserDTO();
        testUserDTO.setId(testUUID);
        testUserDTO.setUsername("username");
        testUserDTO.setEmail("email@test.com");
        testUserDTO.setRole(Role.CUSTOMER);
        return testUserDTO;
    }

    private OrderDTO getSingleOrderDTO(Long orderId, UUID userId) {
        OrderDTO order = new OrderDTO();
        order.setId(orderId);
        order.setStatus(OrderStatus.USER_BROWSING);
        order.setUserId(userId);

        Product product = new Product();
        product.setName("test-product");
        product.setDescription("test-description");
        product.setEUR(9.99);
        product.setStockRemaining(10);

        order.setProducts(List.of(product));

        return order;

    }
}