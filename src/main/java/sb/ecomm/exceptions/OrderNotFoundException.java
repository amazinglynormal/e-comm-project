package sb.ecomm.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Could not find order " + id);
    }

    public OrderNotFoundException() {super("Could not find order");}
}
