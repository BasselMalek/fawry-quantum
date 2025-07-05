import java.util.HashMap;
import java.util.Map;

public class Cart {
    private final Map<String, CartItem> items = new HashMap<>();
    private final Inventory inventory;

    public Cart(Inventory inventory) {
        this.inventory = inventory;
    }
    public  void displayCart() {
        if (this.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("=================== YOUR CART ===================");
        System.out.println();

        items.forEach((String k , CartItem v)-> {
            Item item = v.getItem();

            System.out.println("Item ID: " + k);
            System.out.println("  Name: " + item.getName());
            System.out.println("  Description: " + item.getDescription());
            System.out.println("  Price: $" + String.format("%.2f", item.getPrice()));

            if (item instanceof Shippable) {
                System.out.println("  Weight: " + String.format("%.2f", ((Shippable) item).getWeight()) + " lbs");
            } else {
                System.out.println("  Weight: N/A");
            }

            if (item instanceof Perishable) {
                System.out.println("  Expiry Date: " + ((Perishable) item).getExpiryDate().toString());
            } else {
                System.out.println("  Expiry Date: N/A");
            }

            System.out.println("  Quantity: " + v.getQuantity());
            System.out.println();
        });

        System.out.println("Subtotal: $" + String.format("%.2f",getSubtotal()));
        System.out.println("Shipping: $" + String.format("%.2f",getShippingFee()));
        System.out.println("Total: $" + String.format("%.2f",getTotal()));
        System.out.println("===============================================");
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
        return ShippingService.calculateShippingEstimate(items.values().stream().toList());
    }

    public double getTotal() {
        return getSubtotal()+ getSubtotal()*0.14 + getShippingFee();
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