package models;

public class InvoiceLine {
    private String lineId;
    private Book book;
    private int quantity;
    private double unitPrice;
    private double taxRate;
    private String description;

    public InvoiceLine(String lineId, Book book, int quantity, double unitPrice, double taxRate, String description) {
        this.lineId=lineId; this.book=book; this.quantity=quantity; this.unitPrice=unitPrice; this.taxRate=taxRate; this.description=description;
    }
    public boolean updateQuantity(int value) {
        if (value <= 0) return false;
        quantity=value;
        return true;
    }
    public boolean updateUnitPrice(double value) {
        if (value < 0) return false;
        unitPrice=value;
        return true;
    }
    public double calculateNet() { return quantity * unitPrice; }
    public double calculateTax() { return calculateNet() * Math.max(0,taxRate) / 100.0; }
    public double calculateLineTotal() { return calculateNet() + calculateTax(); }
    public boolean refersTo(Book candidate) { return book == candidate; }
    public String describe() { return lineId + ": " + description + " x" + quantity; }
    public Book getBook() { return book; }
}
