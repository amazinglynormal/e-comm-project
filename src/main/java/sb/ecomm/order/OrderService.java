package sb.ecomm.order;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sb.ecomm.constants.TempSecurityConstants;
import sb.ecomm.exceptions.OrderNotFoundException;
import sb.ecomm.order.dto.*;
import sb.ecomm.user.User;
import sb.ecomm.exceptions.UserNotFoundException;
import sb.ecomm.user.UserRepository;
import sb.ecomm.product.Product;
import sb.ecomm.exceptions.ProductNotFoundException;
import sb.ecomm.product.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository,
                        ModelMapper mapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public OrderDTO findOrderById(Long id) {
        Order order =
                orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        return mapper.map(order, OrderDTO.class);
    }

    private Order createNewOrderObject(CreateOrderDTO createOrderDTO) {
        Order newOrder = mapper.map(createOrderDTO, Order.class);
        List<Product> products = getProductsListFromProductIds(createOrderDTO.getProductIds());
        newOrder.setStatus(OrderStatus.USER_BROWSING);
        newOrder.setProducts(products);
        newOrder.setPaymentStatus(PaymentStatus.UNPAID);

        return newOrder;
    }

    public OrderDTO addNewUserOrder(CreateOrderDTO createOrderDTO, UUID id) {
        Order newOrder = createNewOrderObject(createOrderDTO);

        User user =
                userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        newOrder.setUser(user);

        Order savedOrder = orderRepository.save(newOrder);
        return mapper.map(savedOrder, OrderDTO.class);
    }

    Order createNewGuestOrder(CreateOrderDTO createOrderDTO) {
        Order newOrder = createNewOrderObject(createOrderDTO);

        newOrder.setEmail(createOrderDTO.getEmail());
        newOrder.setPhone(createOrderDTO.getPhone());
        newOrder.setShippingAddress(createOrderDTO.getShippingAddress());

        return orderRepository.save(newOrder);
    }

    public OrderDTO updateOrder(long id, UpdateOrderDTO updateOrderDTO) {
        Order order =
                orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        updateOrderStatus(order, updateOrderDTO);
        updateOrderProducts(order, updateOrderDTO);
        updatePaymentStatus(order, updateOrderDTO);
        updateOrderEmail(order, updateOrderDTO);
        updateOrderPhone(order, updateOrderDTO);
        updateShippingAddress(order, updateOrderDTO);

        Order savedOrder = orderRepository.save(order);

        return mapper.map(savedOrder, OrderDTO.class);
    }

    public CreateCheckoutSessionResponse createCheckoutSession(CreateCheckoutSessionDTO createCheckoutSessionDTO) {
        Stripe.apiKey = TempSecurityConstants.stripeTestKey;

        Order order;

        if (createCheckoutSessionDTO.getOrderId() != null) {
            order = orderRepository.findById(createCheckoutSessionDTO.getOrderId()).orElseThrow(() -> new OrderNotFoundException(createCheckoutSessionDTO.getOrderId()));
        } else {
            CreateOrderDTO newOrder = new CreateOrderDTO();
            newOrder.setProductIds(createCheckoutSessionDTO.getProductIds());
            newOrder.setEmail(createCheckoutSessionDTO.getEmail());
            newOrder.setPhone(createCheckoutSessionDTO.getPhone());
            newOrder.setShippingAddress(createCheckoutSessionDTO.getShippingAddress());
            order = createNewGuestOrder(newOrder);
        }

        List<SessionCreateParams.LineItem> lineItems = getLineItems(createCheckoutSessionDTO.getProductIds(),
                createCheckoutSessionDTO.getCurrency());

        String domain = "http://localhost:8080";
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(domain + "/#/ordersummary")
                .setCancelUrl(domain + "/#/login")
                .addAllLineItem(lineItems)
                .build();

        try {
            Session session = Session.create(params);

            order.setStripeSessionId(session.getId());
            order.setPaymentStatus(PaymentStatus.PENDING);
            order.setStatus(OrderStatus.PENDING_PAYMENT);

            orderRepository.save(order);

            return new CreateCheckoutSessionResponse(session.getId());
        } catch (StripeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private List<SessionCreateParams.LineItem> getLineItems(List<Long> productIds, Currency currency) {
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        HashMap<Long, Long> products = new HashMap<>();

        productIds.forEach(id -> {
            if (products.containsKey(id)) {
                long num = products.get(id);
                products.replace(id, num + 1);
            } else {
                products.put(id, 1L);
            }
        });

        products.forEach((id, units) -> {
            Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

            String lineItem;

            switch (currency) {
                case GBP:
                    lineItem = product.getStripeGbp();
                    break;
                case USD:
                    lineItem = product.getStripeUsd();
                    break;
                case EUR:
                default:
                    lineItem = product.getStripeEur();
                    break;
            }

            lineItems.add(
                    SessionCreateParams.LineItem.builder().setQuantity(units).setPrice(lineItem).build()
            );
        });

        return lineItems;

    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    private List<Product> getProductsListFromProductIds(List<Long> productIds) {
        List<Product> products = new ArrayList<>();
        productIds.forEach(id -> {
            Product product =
                    productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
            products.add(product);
        });

        return products;
    }

    private void updateOrderStatus(Order order, UpdateOrderDTO updateOrderDTO) {
        if (order.getStatus() != updateOrderDTO.getStatus() && updateOrderDTO.getStatus() != null) {
            order.setStatus(updateOrderDTO.getStatus());
        }
    }

    private void updateOrderProducts(Order order, UpdateOrderDTO updateOrderDTO) {
        List<Product> productsOrdered = order.getProducts();

        if (updateOrderDTO.getAddProduct() != null) {
            Product product =
                    productRepository.findById(updateOrderDTO.getAddProduct()).orElseThrow(() -> new ProductNotFoundException(updateOrderDTO.getAddProduct()));
            productsOrdered.add(product);
        }

        if (updateOrderDTO.getRemoveProduct() != null) {
            productsOrdered.removeIf(product -> product.getId().equals(updateOrderDTO.getRemoveProduct()));
        }

        order.setProducts(productsOrdered);

    }

    private void updatePaymentStatus(Order order, UpdateOrderDTO updateOrderDTO) {
        if (order.getPaymentStatus() != updateOrderDTO.getPaymentStatus() && updateOrderDTO.getPaymentStatus() != null) {
            order.setPaymentStatus(updateOrderDTO.getPaymentStatus());
        }
    }

    private void updateOrderEmail(Order order, UpdateOrderDTO updateOrderDTO) {
        if (order.getEmail().equals(updateOrderDTO.getEmail()) && updateOrderDTO.getEmail() != null) {
            order.setEmail(updateOrderDTO.getEmail());
        }
    }

    private void updateOrderPhone(Order order, UpdateOrderDTO updateOrderDTO) {
        if (order.getPhone().equals(updateOrderDTO.getPhone()) && updateOrderDTO.getPhone() != null) {
            order.setPhone(updateOrderDTO.getPhone());
        }
    }

    private void updateShippingAddress(Order order, UpdateOrderDTO updateOrderDTO) {
        if (order.getShippingAddress() != updateOrderDTO.getShippingAddress() && updateOrderDTO.getShippingAddress() != null) {
            order.setShippingAddress(updateOrderDTO.getShippingAddress());
        }
    }
}
