package sb.ecomm.order;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sb.ecomm.user.User;
import sb.ecomm.user.UserNotFoundException;
import sb.ecomm.user.UserRepository;
import sb.ecomm.order.dto.CreateOrderDTO;
import sb.ecomm.order.dto.OrderDTO;
import sb.ecomm.order.dto.UpdateOrderDTO;
import sb.ecomm.product.Product;
import sb.ecomm.product.ProductNotFoundException;
import sb.ecomm.product.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private ModelMapper mapper;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository, ModelMapper mapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    OrderDTO findOrderById(Long id) {
        Order order =
                orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        return mapper.map(order, OrderDTO.class);
    }

    OrderDTO addNewOrder(CreateOrderDTO createOrderDTO) {
        Order newOrder = mapper.map(createOrderDTO, Order.class);
        User user =
                userRepository.findById(createOrderDTO.getUserId()).orElseThrow(() -> new UserNotFoundException(createOrderDTO.getUserId()));
        newOrder.setUser(user);
        List<Product> initialOrderedProducts =
                getInitialOrderProductsList(createOrderDTO.getProductIds());
        newOrder.setProducts(initialOrderedProducts);
        Order savedOrder = orderRepository.save(newOrder);
        return mapper.map(savedOrder, OrderDTO.class);
    }

    OrderDTO updateOrder(long id, UpdateOrderDTO updateOrderDTO) {
        Order order =
                orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        updateOrderStatus(order, updateOrderDTO);
        updateOrderProducts(order, updateOrderDTO);

        Order savedOrder = orderRepository.save(order);

        return mapper.map(savedOrder, OrderDTO.class);
    }

    void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    private List<Product> getInitialOrderProductsList(List<Long> productIds) {
        List<Product> products = new ArrayList<>();
        productIds.forEach(id -> {
            Product product =
                    productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
            products.add(product);
        });

        return products;
    }

    private void updateOrderStatus(Order order, UpdateOrderDTO updateOrderDTO) {
        if (order.getStatus() != updateOrderDTO.getStatus()) {
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
}
