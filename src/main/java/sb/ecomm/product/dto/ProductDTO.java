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

    private int sizeEUR;

    private int sizeUK;

    private int sizeUS;

    private String collection;

    private Long categoryId;
}
