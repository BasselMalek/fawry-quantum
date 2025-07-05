import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<String, CartItem> items = new HashMap<>();
    private Inventory inventory;

    public Cart(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean addItem(String itemId, int type, int quantity) {
        if (!inventory.hasItem(itemId, quantity, type)) {
            return false;
        }

        CartItem existingItem = items.get(itemId);
        if (existingItem != null) {
            existingItem.quantity += quantity;
        } else {
            ItemEntry inventoryItem = inventory.getItem(itemId, type);
            if (inventoryItem != null) {
                items.put(itemId, new CartItem(inventoryItem.getItem(), quantity));
            }
        }
        return true;
    }

    public boolean removeItem(String itemId, int quantity) {
        CartItem cartItem = items.get(itemId);
        if (cartItem == null) {
            return false;
        }

        cartItem.quantity -= quantity;
        if (cartItem.quantity <= 0) {
            items.remove(itemId);
        }
        return true;
    }

    public double getSubtotal() {
        return items.values().stream()
                .mapToDouble(item -> item.item.getPrice() * item.quantity)
                .sum();
    }

    public double getShippingFee() {
        double totalWeight = items.values().stream()
                .filter(cartItem -> cartItem.item instanceof Shippable)
                .mapToDouble(cartItem -> {
                    Shippable shippable = (Shippable) cartItem.item;
                    return shippable.getWeight() * cartItem.quantity;
                })
                .sum();

        return totalWeight > 0 ? Math.max(19.99, totalWeight * 1.50) : 0.0;
    }

    public double getTotal() {
        return getSubtotal() + getShippingFee();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Map<String, CartItem> getItems() {
        return new HashMap<>(items);
    }

    public void clear() {
        items.clear();
    }

    // Inner class for cart items
    public static class CartItem {
        public final Item item;
        public int quantity;

        public CartItem(Item item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }
    }
}