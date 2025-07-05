import java.util.List;

public class ShippingOrder {
    private String id;
    private String address;
    private List<CartItem> items;
    private double price;

    public ShippingOrder(String id, String address, List<CartItem> items, double price) {
        this.id = id;
        this.address = address;
        this.items = items;
        this.price = price;
    }

    public void printShippingManifest() {
        System.out.println("=============== SHIPPING MANIFEST ===============");
        System.out.println("Order ID: " + id);
        System.out.println("Shipping Address: " + address);
        System.out.println("Shipping Price: $" + String.format("%.2f", price));
        System.out.println("================================================");
        System.out.println("Items to Ship:");
        System.out.println();

        for (CartItem cartItem : items) {
            Item item = cartItem.getItem();
            System.out.println("Item: " + item.getName());
            System.out.println("  Weight: " + String.format("%.2f", ((Shippable)item).getWeight()) + " lbs");
            System.out.println("  Quantity: " + cartItem.getQuantity());
            System.out.println();
        }

        double totalWeight = items.stream().mapToDouble(ci -> ((Shippable)ci.getItem()).getWeight() * ci.getQuantity()).sum();
        int totalItems = items.stream().mapToInt(CartItem::getQuantity).sum();
        System.out.println("Total Weight: " + String.format("%.2f", totalWeight) + " lbs");
        System.out.println("Total Items: " + totalItems);
        System.out.println("===============================================");
    }

    private String truncate(String str, int maxLength) {
        if (str == null) return "";
        return str.length() > maxLength ? str.substring(0, maxLength - 3) + "..." : str;
    }

    // Getters
    public double getPrice() { return price; }
}