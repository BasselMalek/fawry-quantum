public class ItemEntry {
        private Item item;
        private int quantity;

        public ItemEntry(Item item, int quantity){
            this.item = item;
            this.quantity = quantity;
        }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }
}
