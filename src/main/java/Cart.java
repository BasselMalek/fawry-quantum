import java.util.HashMap;
import java.util.Map;

public class Cart {
    private final Map<String, CartItem> items = new HashMap<>();
    private final Inventory inventory;

    public Cart(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean addItem(String itemId, int type, int quantity) {
        if (!inventory.hasItem(itemId, quantity, type)) {
            System.out.println("hit");
            return false;
        }

        CartItem existingItem = items.get(itemId);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity()+ quantity);
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
        cartItem.setQuantity(cartItem.getQuantity()- quantity);

        if (cartItem.getQuantity() <= 0) {
            items.remove(itemId);
        }
        return true;
    }

    public double getSubtotal() {
        return items.values().stream()
                .mapToDouble(item -> item.getItem().getPrice() * item.getQuantity())
                .sum();
    }

    public double getShippingFee() {
        double totalWeight = items.values().stream()
                .filter(cartItem -> cartItem.getItem() instanceof Shippable)
                .mapToDouble(cartItem -> {
                    Shippable shippable = (Shippable) cartItem.getItem();
                    return shippable.getWeight() * cartItem.getQuantity();
                })
                .sum();

        return totalWeight > 0 ? Math.max(19.99, totalWeight * 0.85) : 0.0;
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
}