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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double USD;

    @Column(nullable = false)
    private double EUR;

    @Column(nullable = false)
    private double GBP;

    @Column(nullable = false)
    private String imageSrc;

    @Column(nullable = false)
    private String imageAlt;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private boolean inStock;

    @Column(nullable = false)
    private int stockRemaining;

    @Column
    private String stripeEur;

    @Column
    private String stripeGbp;

    @Column
    private String stripeUsd;

    @JsonIgnoreProperties("products")
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, referencedColumnName
            = "id", foreignKey = @ForeignKey(name = "category_product_fk"))
    private Category category;

    public Product() {
    }

    public Product(String name,
                   String description,
                   double USD,
                   double EUR,
                   double GBP,
                   String imageSrc,
                   String imageAlt,
                   Color color,
                   String size,
                   boolean inStock,
                   int stockRemaining,
                   Category category) {
        this.name = name;
        this.description = description;
        this.USD = USD;
        this.EUR = EUR;
        this.GBP = GBP;
        this.imageSrc = imageSrc;
        this.imageAlt = imageAlt;
        this.color = color;
        this.size = size;
        this.inStock = inStock;
        this.stockRemaining = stockRemaining;
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

    public double getUSD() {
        return USD;
    }

    public void setUSD(double USD) {
        this.USD = USD;
    }

    public double getEUR() {
        return EUR;
    }

    public void setEUR(double EUR) {
        this.EUR = EUR;
    }

    public double getGBP() {
        return GBP;
    }

    public void setGBP(double GBP) {
        this.GBP = GBP;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public int getStockRemaining() {
        return stockRemaining;
    }

    public void setStockRemaining(int stockRemaining) {
        this.stockRemaining = stockRemaining;
    }

    public String getStripeEur() {
        return stripeEur;
    }

    public void setStripeEur(String stripeEur) {
        this.stripeEur = stripeEur;
    }

    public String getStripeGbp() {
        return stripeGbp;
    }

    public void setStripeGbp(String stripeGbp) {
        this.stripeGbp = stripeGbp;
    }

    public String getStripeUsd() {
        return stripeUsd;
    }

    public void setStripeUsd(String stripeUsd) {
        this.stripeUsd = stripeUsd;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
