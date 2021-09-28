package sb.ecomm.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductDTO {

    private String name;

    private String description;

    private String features;

    private double priceCAD;

    private double priceUSD;

    private double priceAUD;

    private double priceEUR;

    private double priceGBP;

    private Long categoryId;
}
