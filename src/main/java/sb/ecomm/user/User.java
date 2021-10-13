package sb.ecomm.user;

import sb.ecomm.order.Order;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity(name = "User")
@Table(name = "\"user\"", uniqueConstraints = {@UniqueConstraint(name =
        "user_email_unique", columnNames = "email")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String refreshToken;

    @Column
    private String verificationHash;

    @Column
    private String passwordResetToken;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @Column(columnDefinition = "boolean DEFAULT false NOT NULL")
    private boolean isEnabled;

    @Column(columnDefinition = "boolean DEFAULT true NOT NULL")
    private boolean isAccountNonExpired;

    @Column(columnDefinition = "boolean DEFAULT true NOT NULL")
    private boolean isAccountNonLocked;

    @Column(columnDefinition = "boolean DEFAULT true NOT NULL")
    private boolean isCredentialsNonExpired;

    public User() {
    }

    public User(String username,
                String email,
                String password,
                Role role, String verificationHash,
                Set<Order> orders) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.verificationHash = verificationHash;
        this.orders = orders;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getVerificationHash() {
        return verificationHash;
    }

    public void setVerificationHash(String verificationHash) {
        this.verificationHash = verificationHash;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
