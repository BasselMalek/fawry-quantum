import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private double balance;
    private Cart currentCart;
    private List<Order> orderHistory;

    public Customer(String username, String email, String password, double initialBalance) {
        super(username, email, password);
        this.balance = initialBalance;
        this.currentCart = new Cart();
        this.orderHistory = new ArrayList<>();
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

        double subtotal = currentCart.getSubtotal();
        double shippingFee = currentCart.calculateShippingFee();
        double total = subtotal + shippingFee;
        // Check if cx is broke
        if (balance < total) {
            System.out.println("Error: Insufficient balance");
            System.out.printf("Required: $%.2f, Available: $%.2f%n", total, balance);
            return false;
        }
        //Check if the store is broke
        for (CartItem item : currentCart.getItems()) {
            if (!inventory.hasItem(item.getProduct().getId(), item.getQuantity(), item.getProduct().getgetType())) {
                System.out.println("Error: " + item.getProduct().getName() + " is out of stock or expired");
                return false;
            }
        }
        //Update cx and store
        for (CartItem item : currentCart.getItems()) {
            inventory.reduceQuantity(item.getProduct().getName(), item.getQuantity());
        }
        modifyBalance(-total);

        //Add to history and print
        Order order = new Order(this, new ArrayList<>(currentCart.getItems()), subtotal, shippingFee);
        orderHistory.add(order);
        order.printCheckoutDetails();
        clearCart();
        System.out.println("Order completed successfully!");
        // ShippingService.handleShipment(order);
        return true;
    }



    private void printCheckoutDetails(double subtotal, double shippingFee, double total) {
        System.out.println("\n=== CHECKOUT DETAILS ===");
        System.out.printf("Order subtotal: $%.2f%n", subtotal);
        System.out.printf("Shipping fees: $%.2f%n", shippingFee);
        System.out.printf("Total amount: $%.2f%n", total);
        System.out.printf("Remaining balance: $%.2f%n", balance);
    }

    private void handleShipping() {
        List<ShippableProduct> shippableItems = new ArrayList<>();

        for (CartItem item : currentCart.getItems()) {
            if (item.getProduct().isShippable()) {
                shippableItems.add(item.getProduct());
            }
        }

        if (!shippableItems.isEmpty()) {
            ShippingService shippingService = new ShippingService();
            shippingService.processShippableItems(shippableItems);
        }
    }

    // Utility methods
    public void viewCart() {
        currentCart.displayCart();
    }

    public void viewOrderHistory() {
        System.out.println("\n=== ORDER HISTORY ===");
        if (orderHistory.isEmpty()) {
            System.out.println("No previous orders.");
            return;
        }

        for (int i = 0; i < orderHistory.size(); i++) {
            System.out.println("Order #" + (i + 1));
            orderHistory.get(i).displayOrder();
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return String.format("Customer: %s (Balance: $%.2f)", getUsername(), balance);
    }
}