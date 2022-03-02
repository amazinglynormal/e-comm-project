package sb.ecomm.order;

import javax.persistence.Embeddable;

@Embeddable
public class PaymentMethodDetails {

    private String type;
    private String brand;
    private String country;
    private String lastFour;

    public PaymentMethodDetails() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLastFour() {
        return lastFour;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }
}
