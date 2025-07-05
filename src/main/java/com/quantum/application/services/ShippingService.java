package com.quantum.application.services;

import com.quantum.domain.dtos.CartItem;
import com.quantum.domain.interfaces.Item;
import com.quantum.domain.interfaces.Shippable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShippingService {
    private final Map<String, ShippingOrder> shippingOrders;

    public ShippingService() {
        this.shippingOrders = new HashMap<>();
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

    public static double calculateShippingEstimate(List<CartItem> items) {
        double totalWeight = items.stream().filter(ci -> ci.getItem() instanceof Shippable).mapToDouble(ci -> ((Shippable) ci.getItem()).getWeight() * ci.getQuantity())
                .sum();
        double price = 15 + totalWeight * 0.75;
        return totalWeight==0.0?0.0:price;
    }

    public void printShippingOrderReceipt(String orderId){
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
        double price = 15 + totalWeight * 0.75;
        return totalWeight==0.0?0.0:price;
    }
}

//tbh if we were following clean to the dot this whole file would get me fired but splitting this into
// service/order/repo would be too much overengineering for the functionality
class ShippingOrder {
    private final String id;
    private final String address;
    private final List<CartItem> items;
    private final double price;

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
    public double getPrice() { return price; }
}