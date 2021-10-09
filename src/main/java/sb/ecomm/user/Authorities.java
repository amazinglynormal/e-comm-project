package sb.ecomm.user;

public enum Authorities {
    PRODUCT_READ("product:read"),
    PRODUCT_WRITE("product:write"),
    CATEGORY_READ("category:read"),
    CATEGORY_WRITE("category:write"),
    ORDER_READ("order:read"),
    ORDER_WRITE("order:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String authority;

    Authorities(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
