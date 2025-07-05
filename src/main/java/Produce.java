import java.util.Date;
import java.util.UUID;

public class Produce implements Perishable, Shippable, Item {
    String Id;
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
        this.Id = String.valueOf((this.expiryDate.toString() + this.name).hashCode());
    }

    @Override
    public String getId() {
        return Id;
    }

    @Override
    public int getType() {
        return 2;
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

    @Override
    public Boolean isExpired() {
        return this.getExpiryDate().before(new Date());
    }
}
