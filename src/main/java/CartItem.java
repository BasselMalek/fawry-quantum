public class CartItem {
    private final Item item;
    private int quantity;

    public Item getItem() {
        return item;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}