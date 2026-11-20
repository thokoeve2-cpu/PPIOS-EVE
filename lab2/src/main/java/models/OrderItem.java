
package models;

public class OrderItem {

    private Book book;
    private int quantity;
    private double unitPrice;

    public OrderItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
        this.unitPrice = book.getPrice();
    }

    public double calculateSubtotal() {
        return unitPrice * quantity;
    }

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void displayItem() {
        System.out.println("Book: " + book.getTitle());
        System.out.println("Quantity: " + quantity);
        System.out.println("Unit price: " + unitPrice);
        System.out.println("Subtotal: " + calculateSubtotal());
    }
}
