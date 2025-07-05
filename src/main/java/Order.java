import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class Order {
    private final String orderId;
    private final List<CartItem> items;
    private final double orderTotal;
    private String status; //should be enum but i'm not even gonna use this rn this demo already getting feature bloat;
    private final double subtotal;
    private final double shippingTotal;


    public Order(String orderId, List<CartItem> items, String status, ShippingService shippingService) {
        this.orderId = UUID.randomUUID().toString();
        this.items = items;
        this.status = "Processing";
        this.subtotal = calcSubtotal();
        this.shippingTotal = shippingService.getOrderPrice(this.orderId);
        this.orderTotal = calcOrderTotal();
    }

    public String getOrderId() {
        return orderId;
    }

    public double calcSubtotal() {
        return items.stream().mapToDouble((CartItem v)->v.getItem().getPrice()).sum();
    }

    public double calcOrderTotal(){
        return subtotal + subtotal * 0.14 + shippingTotal;
    }

    public void printReceipt() {
        System.out.println("=================== RECEIPT ===================");
        System.out.println("Order ID: " + orderId);
        System.out.println("Status: " + status);
        System.out.println("===============================================");
        System.out.println("Items Purchased:");
        System.out.println();

        for (CartItem cartItem : items) {
            Item item = cartItem.getItem();
            System.out.println("Item: " + item.getName());
            System.out.println("  Price: $" + String.format("%.2f", item.getPrice()));
            System.out.println("  Quantity: " + cartItem.getQuantity());
            System.out.println("  Total: $" + String.format("%.2f", item.getPrice() * cartItem.getQuantity()));
            System.out.println();
        }

        System.out.println("Subtotal: $" + String.format("%.2f", subtotal));
        System.out.println("VAT tax: $" + String.format("%.2f", subtotal*0.14));
        System.out.println("Shipping fees: $" + String.format("%.2f", shippingTotal));
        System.out.println("Total: $" + String.format("%.2f", calcOrderTotal()));
        System.out.println("Items ordered: " + items.stream().mapToInt(CartItem::getQuantity).sum());
        System.out.println("===============================================");
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getOrderTotal() {
        return orderTotal;
    }
}