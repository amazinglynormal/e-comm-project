package sb.ecomm.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sb.ecomm.order.Order;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "User")
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany
    private Set<Order> orders;

    @Column
    private String refreshToken;

    @Column
    private boolean active;

    @Column
    private String verificationHash;

    @Column
    private String resetToken;
}
