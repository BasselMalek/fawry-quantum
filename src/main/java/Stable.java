public class Stable implements Item, Shippable{
    String Id;
    String name;
    String description;
    Double price;

    Double weight;

    public Stable(String name, String description, Double price, Double weight) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.Id = String.valueOf((this.name).hashCode());

    }

    @Override
    public String getId() {
        return Id.toString();
    }

    @Override
    public int getType() {
        return 1;
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
