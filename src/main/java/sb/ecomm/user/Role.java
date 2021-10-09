package sb.ecomm.user;

import com.google.common.collect.Sets;

import java.util.Set;

import static sb.ecomm.user.Authorities.*;

public enum Role {
    CUSTOMER(Sets.newHashSet(PRODUCT_READ, ORDER_READ, ORDER_WRITE, USER_READ
            , USER_WRITE, CATEGORY_READ)),
    ADMIN(Sets.newHashSet(PRODUCT_READ, PRODUCT_WRITE, ORDER_READ,
            ORDER_WRITE, CATEGORY_READ, CATEGORY_WRITE, USER_READ, USER_WRITE));

    private final Set<Authorities> authorities;

    Role(Set<Authorities> authorities) {
        this.authorities = authorities;
    }

    public Set<Authorities> getAuthorities() {
        return authorities;
    }
}
