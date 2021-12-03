package sb.ecomm.product.dto;

import lombok.Getter;
import lombok.Setter;
import sb.ecomm.product.Color;

import java.util.List;

@Getter
@Setter
public class UpdateProductDTO {

    private String name;

    private String description;

    private String imageSrc;

    private String imageAlt;

    private double USD;

    private double EUR;

    private double GBP;

    private Color color;

    private List<String> allSizes;

    private List<String> availableSizes;

    private Long categoryId;
}
