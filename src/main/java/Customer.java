import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private double balance;
    private final Cart currentCart;
//    private List<Order> orderHistory;

    public Customer(String username, String email, String password, double initialBalance, Inventory inventory) {
        super(username, email, password);
        this.balance = initialBalance;
        this.currentCart = new Cart(inventory);
//        this.orderHistory = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public Cart getCurrentCart() {
        return currentCart;
    }

//    public List<Order> getOrderHistory() {
//        return new ArrayList<>(orderHistory);
//    }

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

        double subtotal = currentCart.getSubtotal();
        double shippingFee = currentCart.getShippingFee();
        double total = currentCart.getTotal();
        // Check if cx is broke
        if (balance < total) {
            System.out.println("Error: Insufficient balance");
            System.out.printf("Required: $%.2f, Available: $%.2f%n", total, balance);
            return false;
        }
        //Check if the store is broke
        // after implementing the cart this whole part kinda redundant
//        for (CartItem item : currentCart.getItems()) {
//            if (!inventory.hasItem(item.getProduct().getId(), item.getQuantity(), item.getProduct().getgetType())) {
//                System.out.println("Error: " + item.getProduct().getName() + " is out of stock or expired");
//                return false;
//            }
//        }
        //Update cx and store
        for (CartItem item : currentCart.getItems().values()) {
            inventory.updateQuantity(item.getItem().getName(), item.getItem().getType(), inventory.getItem(item.getItem().getId(), item.getItem().getType()).getQuantity() - item.getQuantity());
        }
        modifyBalance(-total);

        //Add to history and print
//        Order order = new Order(this, new ArrayList<>(currentCart.getItems()), subtotal, shippingFee);
//        orderHistory.add(order);
//        order.printCheckoutDetails();
        currentCart.clear();
        System.out.println("Order completed successfully!");
        // ShippingService.handleShipment(order);
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

    @Override
    public String toString() {
        return String.format("Customer: %s (Balance: $%.2f)", getUsername(), balance);
    }
}