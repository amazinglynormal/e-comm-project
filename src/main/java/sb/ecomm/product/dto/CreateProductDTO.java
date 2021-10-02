package sb.ecomm.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductDTO {

    private String name;

    private String description;

    private String features;

    private double priceUSD;

    private double priceEUR;

    private double priceGBP;

    private Long categoryId;
}
