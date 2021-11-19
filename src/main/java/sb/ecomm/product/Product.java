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
    private int sizeEUR;

    @Column(nullable = false)
    private int sizeUK;

    @Column(nullable = false)
    private int sizeUS;

    @JsonIgnoreProperties("products")
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, referencedColumnName
            = "id", foreignKey = @ForeignKey(name = "category_product_fk"))
    private Category category;

    @Column(nullable = false)
    private String collection;

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
                   int sizeEUR,
                   int sizeUK,
                   int sizeUS,
                   Category category,
                   String collection) {
        this.name = name;
        this.description = description;
        this.USD = USD;
        this.EUR = EUR;
        this.GBP = GBP;
        this.imageSrc = imageSrc;
        this.imageAlt = imageAlt;
        this.color = color;
        this.sizeEUR = sizeEUR;
        this.sizeUK = sizeUK;
        this.sizeUS = sizeUS;
        this.category = category;
        this.collection = collection;
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

    public int getSizeEUR() {
        return sizeEUR;
    }

    public void setSizeEUR(int sizeEUR) {
        this.sizeEUR = sizeEUR;
    }

    public int getSizeUK() {
        return sizeUK;
    }

    public void setSizeUK(int sizeUK) {
        this.sizeUK = sizeUK;
    }

    public int getSizeUS() {
        return sizeUS;
    }

    public void setSizeUS(int sizeUS) {
        this.sizeUS = sizeUS;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}
