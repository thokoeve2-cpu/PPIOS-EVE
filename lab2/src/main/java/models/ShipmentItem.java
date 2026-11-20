package models;

public class ShipmentItem {
    private String itemId;
    private Book book;
    private int quantity;
    private double declaredValue;
    private String packagingType;
    private boolean inspected;

    public ShipmentItem(String itemId, Book book, int quantity,
                        double declaredValue, String packagingType) {
        this.itemId = itemId;
        this.book = book;
        this.quantity = quantity;
        this.declaredValue = declaredValue;
        this.packagingType = packagingType;
        this.inspected = false;
    }

    public boolean inspect() {
        if (book == null || quantity <= 0) return false;
        inspected = true;
        return true;
    }

    public boolean changeQuantity(int value) {
        if (value <= 0) return false;
        quantity = value;
        return true;
    }

    public double calculateDeclaredTotal() { return quantity * declaredValue; }

    public boolean requiresSpecialPackaging() {
        return packagingType != null && packagingType.toLowerCase().contains("protect");
    }

    public int getQuantity() { return quantity; }
    public Book getBook() { return book; }
}
