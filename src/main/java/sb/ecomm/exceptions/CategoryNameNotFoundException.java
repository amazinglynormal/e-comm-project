package sb.ecomm.exceptions;

public class CategoryNameNotFoundException extends RuntimeException{
    public CategoryNameNotFoundException(String name){
        super("Could not find category with the name: " + name);
    }
}
