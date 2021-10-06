package sb.ecomm.customer;

import sb.ecomm.order.Order;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Customer")
@Table(name = "customer", uniqueConstraints = {@UniqueConstraint(name =
        "customer_email_unique", columnNames = "email")})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String refreshToken;

    @Column(columnDefinition = "boolean DEFAULT false NOT NULL")
    private boolean active;

    @Column
    private String verificationHash;

    @Column
    private String resetToken;

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;

    public Customer() {
    }

    public Customer(String firstName, String email, String password, String refreshToken,
                    boolean active, String verificationHash, String resetToken,
                    Set<Order> orders) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.refreshToken = refreshToken;
        this.active = active;
        this.verificationHash = verificationHash;
        this.resetToken = resetToken;
        this.orders = orders;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getVerificationHash() {
        return verificationHash;
    }

    public void setVerificationHash(String verificationHash) {
        this.verificationHash = verificationHash;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
