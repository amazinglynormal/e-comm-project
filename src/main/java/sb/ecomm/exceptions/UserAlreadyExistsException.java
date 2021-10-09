package sb.ecomm.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException() {
        super("Cannot create account with supplied data");
    }
}
