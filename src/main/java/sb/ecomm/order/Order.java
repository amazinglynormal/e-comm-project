package sb.ecomm.order;

import sb.ecomm.user.User;
import sb.ecomm.product.Product;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Order")
@Table(name = "user_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName
            = "id", foreignKey = @ForeignKey(name = "user_order_fk"))
    private User user;

    @OneToMany
    @JoinColumn(name = "product_id", referencedColumnName = "id", foreignKey
            = @ForeignKey(name = "order_product_fk"))
    private List<Product> products;

    public Order() {
    }

    public Order(OrderStatus status, User user) {
        this.status = status;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
