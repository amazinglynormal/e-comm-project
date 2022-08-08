package sb.ecomm.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import sb.ecomm.category.Category;
import sb.ecomm.email.EmailService;
import sb.ecomm.exceptions.OrderNotFoundException;
import sb.ecomm.order.dto.CreateOrderDTO;
import sb.ecomm.order.dto.OrderDTO;
import sb.ecomm.order.dto.UpdateOrderDTO;
import sb.ecomm.product.Color;
import sb.ecomm.product.Product;
import sb.ecomm.product.ProductRepository;
import sb.ecomm.user.Address;
import sb.ecomm.user.Role;
import sb.ecomm.user.User;
import sb.ecomm.user.UserRepository;
import sb.ecomm.user.dto.UpdateUserDTO;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    private final ModelMapper mapper = new ModelMapper();

    private OrderService orderService;

    private final UUID userId = UUID.randomUUID();


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository, productRepository, userRepository, emailService, mapper);
    }

    @Test
    void findOrderById() {
        Order order = getSimpleTestOrder(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderDTO response = orderService.findOrderById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(4, response.getProducts().size());
    }

    @Test
    void findOrderByIdThrowsExceptionWhenOrderNotFound() {
        when(orderRepository.findById(1L)).thenThrow(new OrderNotFoundException(1L));

        assertThrows(OrderNotFoundException.class, () -> orderService.findOrderById(1L), "Could not find order " + 1L);
    }

    @Test
    void findAllOrdersPlacedByUser() {
        Order order1 = getSimpleTestOrder(1L);
        Order order2 = getSimpleTestOrder(2L);

        List<Order> orders = List.of(order1, order2);

        when(orderRepository.findUserOrders(eq(this.userId), anyCollection())).thenReturn(orders);

        List<OrderDTO> response = orderService.findAllOrdersPlacedByUser(this.userId);

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(1L, response.get(0).getId());
        assertEquals(2L, response.get(1).getId());

    }

    @Test
    void addNewUserOrder() {
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();

        Address address = new Address();
        address.setLine1("test address");
        createOrderDTO.setShippingAddress(address);

        Product product = getSingleTestProduct(1L, Color.BLACK, "S", 99L);
        List<Long> productIds = List.of(product.getId());
        createOrderDTO.setProductIds(productIds);

        createOrderDTO.setEmail("test@test.com");
        createOrderDTO.setPhone("1234567");

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        User user = getSingleTestUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Order order = getSimpleTestOrder(69L);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO response = orderService.addNewUserOrder(createOrderDTO, this.userId);

        assertNotNull(response);
        assertEquals(69L, response.getId());
        assertEquals(this.userId, response.getUserId());
        assertEquals(4, response.getProducts().size());

    }

    @Test
    void createNewGuestOrder() {
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();

        Address address = new Address();
        address.setLine1("test address");
        createOrderDTO.setShippingAddress(address);

        Product product = getSingleTestProduct(1L, Color.BLACK, "S", 99L);
        List<Long> productIds = List.of(product.getId());
        createOrderDTO.setProductIds(productIds);

        createOrderDTO.setEmail("test@test.com");
        createOrderDTO.setPhone("1234567");

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Order order = getSimpleTestOrder(11L);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order response = orderService.createNewGuestOrder(createOrderDTO);

        assertNotNull(response);
        assertEquals(11L, response.getId());
        assertEquals(this.userId, response.getUser().getId());
        assertEquals(4, response.getProducts().size());
    }

    @Test
    void updateOrder() {
        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
        updateOrderDTO.setUserId(this.userId);

        Address address = new Address();
        address.setLine1("test address");
        updateOrderDTO.setShippingAddress(address);

        String updatedEmail = "updated@test.com";
        updateOrderDTO.setEmail(updatedEmail);

        Order order= getSimpleTestOrder(22L);
        when(orderRepository.findById(22L)).thenReturn(Optional.of(order));

        order.setEmail(updatedEmail);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO response = orderService.updateOrder(22L, updateOrderDTO);

        assertNotNull(response);
        assertEquals(22L, response.getId());
        assertEquals(this.userId, response.getUserId());
        assertEquals(4, response.getProducts().size());

    }

    private Order getSimpleTestOrder(Long id ) {
        Order order = new Order();
        order.setId(id);
        order.setStatus(OrderStatus.USER_BROWSING);
        order.setPaymentStatus(PaymentStatus.UNPAID);

        User user = getSingleTestUser();
        order.setUser(user);

        List<Product> products = getTestProducts();
        order.setProducts(products);

        Address address = new Address();
        address.setLine1("test address");
        order.setShippingAddress(address);

        order.setSubtotal(9.99 * 4);
        order.setShippingCost(9.99);
        order.setTotalCost(9.99 * 5);

        return order;
    }

    private User getSingleTestUser() {
        Set<Order> orders = Collections.emptySet();
        User testUser = new User("username", "email@test.com", "password123", Role.CUSTOMER, "abcde12345", orders);
        testUser.setId(this.userId);
        return testUser;
    }

    private List<Product> getTestProducts() {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            products.add(getSingleTestProduct((long) i, Color.BLACK, "S", 99L));
        }

        return products;
    }

    private Product getSingleTestProduct(Long id, Color color, String size, Long categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        category.setName("test category");
        category.setDescription("test category description");

        Product product = new Product("test product",
                "test description",
                9.99,
                9.99,
                9.99,
                "imageSrc",
                "imageAlt",
                color,
                size,
                true,
                10,
                category);
        product.setId(id);

        return product;
    }
}