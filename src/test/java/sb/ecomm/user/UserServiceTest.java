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
import sb.ecomm.exceptions.UserNotFoundException;
import sb.ecomm.order.Order;
import sb.ecomm.order.OrderService;
import sb.ecomm.user.dto.CreateUserDTO;
import sb.ecomm.user.dto.UserDTO;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    void updateUserAccount() {
    }

    @Test
    void deleteUserAccount() {
    }

    @Test
    void findUserOrderById() {
    }

    @Test
    void getAllOrdersPlacedByUser() {
    }

    @Test
    void addNewUserOrder() {
    }

    @Test
    void updateUserOrder() {
    }

    @Test
    void createCheckoutSession() {
    }

    @Test
    void deleteUserOrder() {
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
}