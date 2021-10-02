package sb.ecomm.order;

import sb.ecomm.customer.Customer;
import sb.ecomm.product.Product;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Order")
@Table(name = "customer_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, referencedColumnName
            = "id", foreignKey = @ForeignKey(name = "customer_order_fk"))
    private Customer customer;

    @OneToMany
    @JoinColumn(name = "product_id", referencedColumnName = "id", foreignKey
            = @ForeignKey(name = "order_product_fk"))
    private List<Product> products;

    public Order() {
    }

    public Order(OrderStatus status, Customer customer) {
        this.status = status;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
