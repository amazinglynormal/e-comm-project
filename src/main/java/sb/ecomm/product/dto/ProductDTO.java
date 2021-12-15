package sb.ecomm.product.dto;

import lombok.Getter;
import lombok.Setter;
import sb.ecomm.product.Color;

@Getter
@Setter
public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private double USD;

    private double EUR;

    private double GBP;

    private String imageAlt;

    private String imageSrc;

    private Color color;

    private String size;

    private boolean inStock;

    private Long categoryId;
}
