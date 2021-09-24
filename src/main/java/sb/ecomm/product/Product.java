package sb.ecomm.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sb.ecomm.category.Category;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Product")
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Category category;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String features;

    @Column
    private double priceCAD;

    @Column
    private double priceUSD;

    @Column
    private double priceAUD;

    @Column
    private double priceEUR;

    @Column
    private double priceGBP;
}
