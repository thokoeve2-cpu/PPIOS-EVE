
package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {

    private String orderId;
    private Customer customer;
    private ArrayList<OrderItem> items;
    private double totalAmount;
    private String orderStatus;
    private String orderDate;

    public Order(String orderId, Customer customer, String orderDate) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderDate = orderDate;
        this.items = new ArrayList<>();
        this.totalAmount = 0;
        this.orderStatus = "Pending";
    }

    public void addBook(Book book, int quantity) {
        if (book == null || quantity <= 0) {
            System.out.println("Invalid order item.");
            return;
        }

        if (quantity > book.getQuantity()) {
            System.out.println("Not enough books in stock.");
            return;
        }

        OrderItem item = new OrderItem(book, quantity);
        items.add(item);

        totalAmount += item.calculateSubtotal();

        book.removeStock(quantity);

        System.out.println("Book added to order successfully.");
    }

    public void removeItem(OrderItem item) {
        if (items.remove(item)) {
            totalAmount -= item.calculateSubtotal();
            item.getBook().addStock(item.getQuantity());
            System.out.println("Item removed from order.");
        }
    }

    public void updateOrderStatus(String status) {
        if (status != null && !status.trim().isEmpty()) {
            orderStatus = status;
        }
    }

    public double calculateOrderTotal() {
        return totalAmount;
    }

    public boolean isOrderEmpty() {
        return items.isEmpty();
    }

    public void displayOrder() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + customer.getFullName());
        System.out.println("Order Date: " + orderDate);
        System.out.println("Order Status: " + orderStatus);

        System.out.println("Books in Order: " + items.size());

        for (OrderItem item : items) {
            item.displayItem();
        }

        System.out.println("Order Total: " + totalAmount);
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(items));
    }

    public String getOrderId() {
        return orderId;
    }

}


