import java.util.*;
import java.util.stream.Collectors;

public class ShippingService {
    private final Map<String, ShippingOrder> shippingOrders;

    public ShippingService() {
        this.shippingOrders = new HashMap<>();
    }

    public static double calculateShippingEstimate(List<CartItem> items) {
        double totalWeight = items.stream()
                .filter(ci -> ci.getItem() instanceof Shippable)
                .mapToDouble(ci -> ((Shippable) ci.getItem()).getWeight() * ci.getQuantity())
                .sum();
        return 15 + totalWeight *0.75;
    }

    public void createInternalShippingOrder(String id, String address, List<CartItem> items) {
        List<CartItem> shippableItems = items.stream()
                .filter(ci -> ci.getItem() instanceof Shippable)
                .collect(Collectors.toList());

        double price = calculateShippingFees(shippableItems);
        ShippingOrder order = new ShippingOrder(id, address, shippableItems, price);
        shippingOrders.put(id, order);
    }

    public double getOrderPrice(String orderId) {
        ShippingOrder order = shippingOrders.get(orderId);
        return order != null ? order.getPrice() : 0.0;
    }

    public void printShippingOrderrReceipt(String orderId){
        ShippingOrder order = shippingOrders.get(orderId);
        if (order != null){
            order.printShippingManifest();
        }else{
            System.out.println("Invalid shipping id.");
        }
    }

    private double calculateShippingFees(List<CartItem> items) {
        double totalWeight = items.stream()
                .mapToDouble(ci -> ((Shippable) ci.getItem()).getWeight() * ci.getQuantity())
                .sum();
        return 15 + totalWeight * 0.75;
    }
}