package sb.ecomm.product;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import sb.ecomm.category.Category;

import javax.persistence.*;

@Entity(name = "Product")
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String features;

    @Column
    private double priceUSD;

    @Column
    private double priceEUR;

    @Column
    private double priceGBP;

    @JsonIgnoreProperties("products")
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, referencedColumnName
            = "id", foreignKey = @ForeignKey(name = "category_product_fk"))
    private Category category;

    public Product() {
    }

    public Product(String name, String description, String features,
                   double priceUSD, double priceEUR, double priceGBP, Category category) {
        this.name = name;
        this.description = description;
        this.features = features;
        this.priceUSD = priceUSD;
        this.priceEUR = priceEUR;
        this.priceGBP = priceGBP;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public double getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(double priceUSD) {
        this.priceUSD = priceUSD;
    }


    public double getPriceEUR() {
        return priceEUR;
    }

    public void setPriceEUR(double priceEUR) {
        this.priceEUR = priceEUR;
    }

    public double getPriceGBP() {
        return priceGBP;
    }

    public void setPriceGBP(double priceGBP) {
        this.priceGBP = priceGBP;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
