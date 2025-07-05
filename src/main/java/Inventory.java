import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final Map<String, ItemEntry> perishableItems = new HashMap<>();
    private final Map<String, ItemEntry> stableItems = new HashMap<>();
    private final Map<String, ItemEntry> onlineItems = new HashMap<>();

    public void addItem(Item item, int quantity) {
        switch (item.getType()) {
            case 0:
                onlineItems.put(item.getId(), new ItemEntry(item, quantity));
                break;
            case 1:
                stableItems.put(item.getId(), new ItemEntry(item, quantity));
                break;
            case 2:
                perishableItems.put(item.getId(), new ItemEntry(item, quantity));
                break;
        }
    }

    public ItemEntry getItem(String id, int type) {
        switch (type) {
            case 0:
                return onlineItems.get(id);
            case 1:
                return stableItems.get(id);
            case 2:
                return perishableItems.get(id);
            default:
                break;
        }
        System.out.println("Invalid Type/Item ID.");
        return null;
    }

    public HashMap<String, ItemEntry> getItems(int type) {
        switch (type) {
            case 0:
                return new HashMap<>(onlineItems);
            case 1:
                return new HashMap<>(stableItems);
            case 2:
                return new HashMap<>(perishableItems);
            default:
                break;
        }
        System.out.println("Invalid type ID.");
        return null;
    }

    public void updateQuantity(String id, int type, int newQuantity) {
        ItemEntry entry = getItem(id, type);
        if (entry != null) {
            entry.setQuantity(newQuantity);
        }
    }

    public boolean removeItem(String id, int type) {
        return switch (type) {
            case 0 -> onlineItems.remove(id) != null;
            case 1 -> stableItems.remove(id) != null;
            case 2 -> perishableItems.remove(id) != null;
            default -> false;
        };
    }

    public boolean hasItem(String id, int quantity, int type) {
        ItemEntry i = getItem(id, type);
        if (i == null || i.getItem() == null || i.getQuantity() < quantity) {
            return false;
        }
        if (i.getItem().getType() == 2) {
            return !((Perishable) i.getItem()).isExpired();
        }
        return true;
    }

    public int getTotalItems() {
        return onlineItems.size() + stableItems.size() + perishableItems.size();
    }

    public void displayItems(int type) {
        HashMap<String, ItemEntry> items = this.getItems(type);
        if (items == null || items.isEmpty()) {
            System.out.println("No items available.");
            return;
        }

        System.out.println("================== INVENTORY ==================");
        System.out.println();

        for (Map.Entry<String, ItemEntry> entry : items.entrySet()) {
            ItemEntry itemEntry = entry.getValue();
            Item item = itemEntry.getItem();

            System.out.println("Item ID: " + item.getId());
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

            System.out.println("  Quantity Available: " + itemEntry.getQuantity());
            System.out.println();
        }

        System.out.println("===============================================");
    }

    public void cleanUpPerishables() {
        perishableItems.entrySet().removeIf(entry -> {
            Perishable perishable = (Perishable) entry.getValue().getItem();
            return perishable.isExpired();
        });
    }

}