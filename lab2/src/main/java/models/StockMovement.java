
package models;

public class StockMovement {

    private String movementId;
    private Book book;
    private Employee employee;
    private String movementType;
    private int quantity;
    private String movementDate;
    private String reason;
    private boolean applied;

    public StockMovement(String movementId, Book book,
                         Employee employee, String movementType,
                         int quantity, String movementDate,
                         String reason) {

        this.movementId = movementId;
        this.book = book;
        this.employee = employee;
        this.movementType = movementType;
        this.quantity = quantity;
        this.movementDate = movementDate;
        this.reason = reason;
        this.applied = false;
    }

    public boolean applyMovement() {
        if (!isValidMovement() || applied) {
            return false;
        }

        if (movementType.equalsIgnoreCase("IN")) {
            book.addStock(quantity);
        } else if (movementType.equalsIgnoreCase("OUT")) {
            if (quantity > book.getQuantity()) {
                System.out.println("Insufficient stock.");
                return false;
            }
            book.removeStock(quantity);
        }

        applied = true;
        System.out.println("Stock movement applied successfully.");
        return true;
    }

    public boolean reverseMovement() {
        if (!applied) {
            return false;
        }

        if (movementType.equalsIgnoreCase("IN")) {
            if (quantity > book.getQuantity()) {
                System.out.println("Cannot reverse: insufficient stock.");
                return false;
            }
            book.removeStock(quantity);
        } else {
            book.addStock(quantity);
        }

        applied = false;
        System.out.println("Stock movement reversed successfully.");
        return true;
    }

    public boolean isValidMovement() {
        return book != null
                && employee != null
                && quantity > 0
                && movementType != null
                && (movementType.equalsIgnoreCase("IN")
                || movementType.equalsIgnoreCase("OUT"));
    }

    public void displayMovement() {
        System.out.println("Movement ID: " + movementId);
        System.out.println("Book: " + book.getTitle());
        System.out.println("Employee: " + employee.getFullName());
        System.out.println("Movement Type: " + movementType);
        System.out.println("Quantity: " + quantity);
        System.out.println("Movement Date: " + movementDate);
        System.out.println("Reason: " + reason);
        System.out.println("Applied: " + applied);
    }

    public String getMovementId() {
        return movementId;
    }

    public boolean isApplied() {
        return applied;
    }
}
