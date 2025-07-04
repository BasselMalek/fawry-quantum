import java.util.Date;

public class Produce implements Perishable, Shippable, Item {
    String name;
    String description;
    Double price;

    Double weight;
    Date expiryDate;

    public Produce(String name, String description, Double price, Double weight, Date expiryDate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.expiryDate = expiryDate;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    @Override
    public Double getWeight(){
        return weight;
    }

    @Override
    public Date getExpiryDate() {
        return expiryDate;
    }
}
