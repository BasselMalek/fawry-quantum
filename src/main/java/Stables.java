public class Stables implements Item, Shippable{
    String name;
    String description;
    Double price;

    Double weight;

    public Stables(String name, String description, Double price, Double weight) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
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
}
