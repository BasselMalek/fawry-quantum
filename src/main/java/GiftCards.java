public class GiftCards implements Item{
    String name;
    String description;
    Double price;

    public GiftCards(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
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
}
