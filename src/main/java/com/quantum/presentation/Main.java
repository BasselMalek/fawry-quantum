package com.quantum.presentation;

import com.quantum.application.services.ShippingService;
import com.quantum.domain.entities.*;
import com.quantum.domain.repos.Inventory;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Inventory inventory = new Inventory();
        User loggedInUser = null;
        //Every time i'm reminded java doesn't have a native JSON parsing library smfh.
        // Gift Cards (Type 0)
        inventory.addItem(new GiftCard("Amazon Gift Card", "$25 Amazon Gift Card", 25.00), 50);
        inventory.addItem(new GiftCard("Target Gift Card", "$50 Target Gift Card", 50.00), 30);
        inventory.addItem(new GiftCard("Starbucks Gift Card", "$15 Starbucks Gift Card", 15.00), 25);
        inventory.addItem(new GiftCard("iTunes Gift Card", "$100 iTunes Gift Card", 100.00), 15);
        inventory.addItem(new GiftCard("Steam Gift Card", "$20 Steam Gift Card", 20.00), 40);

        // Stable Items (Type 1)
        inventory.addItem(new Stable("Canned Tomatoes", "Organic diced tomatoes in juice", 2.49, 14.5), 120);
        inventory.addItem(new Stable("Pasta", "Whole wheat spaghetti pasta", 1.99, 1.0), 75);
        inventory.addItem(new Stable("Rice", "Jasmine white rice 5lb bag", 4.99, 5.0), 45);
        inventory.addItem(new Stable("Olive Oil", "Extra virgin olive oil 500ml", 8.99, 1.1), 30);
        inventory.addItem(new Stable("Cereal", "Whole grain oat cereal", 4.49, 1.2), 60);
        inventory.addItem(new Stable("Peanut Butter", "Natural peanut butter 18oz", 5.99, 1.125), 35);
        inventory.addItem(new Stable("Canned Beans", "Black beans in water", 1.29, 15.0), 80);
        inventory.addItem(new Stable("Flour", "All-purpose flour 5lb", 3.49, 5.0), 25);
        inventory.addItem(new Stable("Sugar", "Granulated white sugar 4lb", 3.99, 4.0), 40);
        inventory.addItem(new Stable("Salt", "Sea salt 26oz", 2.99, 1.625), 55);

// Perishable Items (Type 2) - with expiration dates
        LocalDate today = LocalDate.now();

// Fresh items (expire in 5-10 days)
        LocalDate bananaExpiry = today.plusDays(7);
        inventory.addItem(new Produce("Bananas", "Fresh organic bananas per lb", 1.99, 1.0, bananaExpiry), 15);

        LocalDate appleExpiry = today.plusDays(5);
        inventory.addItem(new Produce("Apples", "Gala apples per lb", 2.49, 1.0, appleExpiry), 20);

        LocalDate lettuceExpiry = today.plusDays(8);
        inventory.addItem(new Produce("Lettuce", "Iceberg lettuce head", 1.79, 1.5, lettuceExpiry), 12);

        LocalDate carrotExpiry = today.plusDays(6);
        inventory.addItem(new Produce("Carrots", "Fresh carrots 2lb bag", 1.99, 2.0, carrotExpiry), 18);

        LocalDate broccoliExpiry = today.plusDays(10);
        inventory.addItem(new Produce("Broccoli", "Fresh broccoli crown", 2.99, 1.2, broccoliExpiry), 10);

// Items expiring soon (1-2 days)
        LocalDate strawberryExpiry = today.plusDays(2);
        inventory.addItem(new Produce("Strawberries", "Fresh strawberries 1lb", 3.99, 1.0, strawberryExpiry), 8);

        LocalDate milkExpiry = today.plusDays(1);
        inventory.addItem(new Produce("Milk", "Whole milk 1 gallon", 3.49, 8.6, milkExpiry), 25);

        LocalDate breadExpiry = today.plusDays(3);
        inventory.addItem(new Produce("Bread", "Whole wheat bread loaf", 2.99, 1.5, breadExpiry), 30);

        LocalDate yogurtExpiry = today.minusDays(1);
        inventory.addItem(new Produce("Yogurt", "Greek yogurt 6oz", 1.49, 0.375, yogurtExpiry), 5);

        LocalDate cheeseExpiry = today.minusDays(2);
        inventory.addItem(new Produce("Cheese", "Cheddar cheese 8oz", 4.99, 0.5, cheeseExpiry), 3);
        ShippingService shippingService = new ShippingService();
        Customer cx = new Customer("poorQA", "poorQA@fawry.com", "qa123", "Giza Pyramids",1800.0, inventory, shippingService);

        loggedInUser = cx;
        System.out.println("Hi to Benji's Supermarket!");
        System.out.println("Log in to continue");
        System.out.println();
//        System.out.print("Email: ");
//        String email = scanner.nextLine();
//        System.out.print("Password: ");
//        String password = scanner.nextLine();
//        if (cx.verifyLogin(email, password)) {
//            loggedInUser = cx;
//        } else {
//            System.out.println("Invalid Data.");
//        }
        while (loggedInUser != null) {
            System.out.println("Logged in!");
            System.out.println();
            System.out.println("1. Browse Products");
            System.out.println("2. View Cart");
            System.out.println("3. Checkout");
            System.out.println("4. Refresh store (makes sure all produce are not expired)");
            System.out.println("5. Log Out");
            System.out.println();
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println();
                    System.out.println("Product Categories:");
                    System.out.println("1. Produce");
                    System.out.println("2. Shelf-Stables");
                    System.out.println("3. Gift Cards");
                    System.out.println();
                    System.out.print("Select category: ");
                    int categoryChoice = scanner.nextInt();
                    switch (categoryChoice) {
                        case 1:
                            System.out.println();
                            System.out.println("Produce Inventory:");
                            inventory.displayItems(2);
                            System.out.println();
                            System.out.print("Enter item ID to add to cart or enter q to go back: ");
                            String produceId = scanner.next();
                            if (produceId.equals("q")) {
                                break;
                            }
                            System.out.print("Enter desired quantity to add to cart: ");
                            int quantity = scanner.nextInt();
                            if (((Customer) loggedInUser).getCurrentCart().addItem(produceId, 2, quantity)) {
                                System.out.println("Item added to cart!");
                            } else {
                                System.out.println("Invalid input.");
                            }
                            break;
                        case 2:
                            System.out.println();
                            System.out.println("Shelf-Stables Inventory:");
                            inventory.displayItems(1);
                            System.out.println();
                            System.out.print("Enter item ID to add to cart or enter q to go back: ");
                            String shelfId = scanner.next();
                            if (shelfId.equals("q")) {
                                break;
                            }
                            System.out.print("Enter desired quantity to add to cart: ");
                            int sQuantity = scanner.nextInt();
                            if (((Customer) loggedInUser).getCurrentCart().addItem(shelfId, 1, sQuantity)) {
                                System.out.println("Item added to cart!");
                            } else {
                                System.out.println("Invalid input.");
                            }
                            break;
                        case 3:
                            System.out.println();
                            System.out.println("Gift Cards Inventory:");
                            inventory.displayItems(0);
                            System.out.println();
                            System.out.print("Enter item ID to add to cart or enter q to go back: ");
                            String giftId = scanner.next();
                            if (giftId.equals("q")) {
                                break;
                            }
                            System.out.print("Enter desired quantity to add to cart: ");
                            int gQuantity = scanner.nextInt();
                            if (((Customer) loggedInUser).getCurrentCart().addItem(giftId, 0, gQuantity)) {
                                System.out.println("Item added to cart!");
                            } else {
                                System.out.println("Invalid input.");
                            }
                            break;
                    }
                    break;
                case 2:
                    ((Customer) loggedInUser).getCurrentCart().displayCart();
                    System.out.print("Enter item ID to remove from cart or enter q to go back: ");
                    String id = scanner.next();
                    if (id.equals("q")) {
                        break;
                    }
                    System.out.print("Enter desired quantity to remove from cart: ");
                    int quantity = scanner.nextInt();
                    ((Customer) loggedInUser).getCurrentCart().removeItem(id, quantity);
                    break;
                case 3:
                    boolean checkout = ((Customer) loggedInUser).checkout(inventory);
                    break;
                case 4:
                    inventory.cleanUpPerishables();
                    break;
                case 5:
                    loggedInUser = null;
                    break;
            }
        }
    }
}