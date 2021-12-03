package sb.ecomm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String customerNotFoundHandler(UserNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String orderNotFoundHandler(OrderNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String productNotFoundHandler(ProductNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String categoryNotFoundHandler(CategoryNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CategoryNameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String categoryNameNotFoundHandler(CategoryNameNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private String userAlreadyExistsHandler(UserAlreadyExistsException ex) {
        return ex.getMessage();
    }

}
