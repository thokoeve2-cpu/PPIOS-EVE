
package models;

import java.util.ArrayList;

public class PurchaseOrder {

    private String purchaseOrderId;
    private Supplier supplier;
    private ArrayList<Book> books;
    private ArrayList<Integer> quantities;
    private double totalCost;
    private String orderDate;
    private String expectedDeliveryDate;
    private String status;

    public PurchaseOrder(String purchaseOrderId, Supplier supplier,
                         String orderDate, String expectedDeliveryDate) {
        this.purchaseOrderId = purchaseOrderId;
        this.supplier = supplier;
        this.orderDate = orderDate;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.books = new ArrayList<>();
        this.quantities = new ArrayList<>();
        this.totalCost = 0;
        this.status = "Pending";
    }

    public boolean addBook(Book book, int quantity) {
        if (book == null || quantity <= 0
                || status.equalsIgnoreCase("Delivered")
                || status.equalsIgnoreCase("Cancelled")) {
            return false;
        }

        books.add(book);
        quantities.add(quantity);
        totalCost += book.getPrice() * quantity;

        System.out.println("Book added to purchase order.");
        return true;
    }

    public boolean removeBook(Book book) {
        int index = books.indexOf(book);

        if (index == -1
                || status.equalsIgnoreCase("Delivered")
                || status.equalsIgnoreCase("Cancelled")) {
            return false;
        }

        totalCost -= books.get(index).getPrice()
                * quantities.get(index);

        books.remove(index);
        quantities.remove(index);

        return true;
    }

    public double calculateTotalCost() {
        return totalCost;
    }

    public int calculateTotalQuantity() {
        int total = 0;

        for (int quantity : quantities) {
            total += quantity;
        }

        return total;
    }

    public boolean confirmOrder() {
        if (books.isEmpty()
                || status.equalsIgnoreCase("Cancelled")
                || status.equalsIgnoreCase("Delivered")) {
            return false;
        }

        status = "Confirmed";
        return true;
    }

    public boolean cancelOrder() {
        if (status.equalsIgnoreCase("Delivered")
                || status.equalsIgnoreCase("Cancelled")) {
            return false;
        }

        status = "Cancelled";
        return true;
    }

    public boolean markAsDelivered() {
        if (!status.equalsIgnoreCase("Confirmed")) {
            return false;
        }

        for (int i = 0; i < books.size(); i++) {
            books.get(i).addStock(quantities.get(i));
        }

        status = "Delivered";
        return true;
    }

    public boolean isOrderEmpty() {
        return books.isEmpty();
    }

    public void updateExpectedDeliveryDate(String newDate) {
        if (newDate != null && !newDate.trim().isEmpty()
                && !status.equalsIgnoreCase("Delivered")) {
            expectedDeliveryDate = newDate;
        }
    }

    public void displayPurchaseOrder() {
        System.out.println("Purchase Order ID: " + purchaseOrderId);
        System.out.println("Supplier: " + supplier.getCompanyName());
        System.out.println("Order Date: " + orderDate);
        System.out.println("Expected Delivery: " + expectedDeliveryDate);
        System.out.println("Status: " + status);
        System.out.println("Total Books: " + calculateTotalQuantity());

        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i).getTitle()
                    + " - Quantity: " + quantities.get(i));
        }

        System.out.println("Total Cost: " + totalCost);
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public String getStatus() {
        return status;
    }
}
