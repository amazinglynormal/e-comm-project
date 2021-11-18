package sb.ecomm.product;

public enum Color {

    BLACK("black"),
    BLUE("blue"),
    BROWN("brown"),
    GREEN("green"),
    GREY("grey"),
    MULTI("multi"),
    NAVY("navy"),
    ORANGE("orange"),
    PINK("pink"),
    PURPLE("purple"),
    RED("red"),
    TAN("tan"),
    WHITE("white"),
    YELLOW("yellow");

    private final String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
