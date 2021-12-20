package sb.ecomm.product.dto;

import lombok.Getter;
import lombok.Setter;
import sb.ecomm.product.Color;

import java.util.List;

@Getter
@Setter
public class CreateProductDTO {

    private String name;

    private String description;

    private double USD;

    private double EUR;

    private double GBP;

    private String imageSrc;

    private String imageAlt;

    private Color color;

    private String size;

    private boolean inStock;

    private int stockRemaining;

    private Long categoryId;
}
