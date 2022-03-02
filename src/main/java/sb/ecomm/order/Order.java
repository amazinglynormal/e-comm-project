package sb.ecomm.order;

import sb.ecomm.user.Address;
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

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "user_order_fk")
    )
    private User user;

    @OneToMany
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "order_product_fk")
    )
    private List<Product> products;

    @Embedded
    private Address shippingAddress;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String stripePaymentIntentId;

    @Embedded
    private PaymentMethodDetails paymentMethodDetails;

    public Order() {
    }

    public Order(OrderStatus status, PaymentStatus paymentStatus, User user) {
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.user = user;
    }

    public Order(OrderStatus status, PaymentStatus paymentStatus, User user, List<Product> products) {
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.user = user;
        this.products = products;
    }

    public Order(OrderStatus status, PaymentStatus paymentStatus, List<Product> products, Address shippingAddress, String email, String phone) {
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.products = products;
        this.shippingAddress = shippingAddress;
        this.email = email;
        this.phone = phone;
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

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
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

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStripePaymentIntentId() {
        return stripePaymentIntentId;
    }

    public void setStripePaymentIntentId(String stripePaymentIntentId) {
        this.stripePaymentIntentId = stripePaymentIntentId;
    }

    public PaymentMethodDetails getPaymentMethodDetails() {
        return paymentMethodDetails;
    }

    public void setPaymentMethodDetails(PaymentMethodDetails paymentMethodDetails) {
        this.paymentMethodDetails = paymentMethodDetails;
    }
}
