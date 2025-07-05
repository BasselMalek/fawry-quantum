package com.quantum.domain.entities;

import com.quantum.application.services.Cart;
import com.quantum.application.services.ShippingService;
import com.quantum.domain.dtos.QuantifiedEntry;
import com.quantum.domain.repos.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer extends User {
    private double balance;
    private final Cart currentCart;
    private String address;
    private final List<Order> orderHistory;
    private final ShippingService shippingService;


    public Customer(String username, String email, String password, String address, double initialBalance, Inventory inventory, ShippingService shippingService) {
        super(username, email, password);
        this.balance = initialBalance;
        this.address = address;
        this.currentCart = new Cart(inventory);
        this.orderHistory = new ArrayList<>();
        this.shippingService = shippingService;
    }

    public double getBalance() {
        return balance;
    }

    public Cart getCurrentCart() {
        return currentCart;
    }

    public List<Order> getOrderHistory() {
        return new ArrayList<>(orderHistory);
    }

    public boolean modifyBalance(double amount) {
        if (this.balance + amount >= 0) {
            this.balance += amount;
            return true;
        }
        return false;
    }

    public boolean checkout(Inventory inventory) {
        if (currentCart.isEmpty()) {
            System.out.println("Error: Cart is empty");
            return false;
        }

        String orderId = UUID.randomUUID().toString();
        shippingService.createInternalShippingOrder(orderId, address, currentCart.getItems().values().stream().toList());
        Order order = new Order(orderId, currentCart.getItems().values().stream().toList(), "Pending", shippingService);
        orderHistory.add(order);

        // Check if cx is broke
        if (balance < order.getOrderTotal()) {
            System.out.println("Error: Insufficient balance");
            System.out.printf("Required: $%.2f, Available: $%.2f%n", order.getOrderTotal(), balance);
            order.setStatus("Canceled");
            return false;
        }
        //Check if the store is broke
        // after implementing the cart this whole part kinda redundant
//        for (QuantifiedEntry item : currentCart.getItems()) {
//            if (!inventory.hasItem(item.getProduct().getId(), item.getQuantity(), item.getProduct().getgetType())) {
//                System.out.println("Error: " + item.getProduct().getName() + " is out of stock or expired");
//                return false;
//            }
//        }
        //Update cx and store
        for (QuantifiedEntry item : currentCart.getItems().values()) {
            inventory.updateQuantity(item.getItem().getId(), item.getItem().getType(), inventory.getItem(item.getItem().getId(), item.getItem().getType()).getQuantity() - item.getQuantity());
        }
        modifyBalance(-order.getOrderTotal());
        currentCart.clear();
        order.setStatus("Processing");
        shippingService.printShippingOrderReceipt(orderId);
        order.printReceipt();
        System.out.println("New balance: $" + String.format("%.2f", balance));
        System.out.println("Order completed successfully!");
        return true;
    }

//
//    public void viewOrderHistory() {
//        System.out.println("\n=== ORDER HISTORY ===");
//        if (orderHistory.isEmpty()) {
//            System.out.println("No previous orders.");
//            return;
//        }
//
//        for (int i = 0; i < orderHistory.size(); i++) {
//            System.out.println("Order #" + (i + 1));
//            orderHistory.get(i).displayOrder();
//            System.out.println();
//        }
//    }
public String getAddress() {
    return address;
}

    public void setAddress(String address) {
        this.address = address;
    }
}