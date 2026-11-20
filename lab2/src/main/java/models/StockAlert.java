
package models;

public class StockAlert {

    private String alertId;
    private Book book;
    private int minimumStockLevel;
    private int currentStock;
    private String alertDate;
    private String alertType;
    private String alertStatus;
    private String message;

    public StockAlert(String alertId, Book book,
                      int minimumStockLevel, String alertDate) {
        this.alertId = alertId;
        this.book = book;
        this.minimumStockLevel = minimumStockLevel;
        this.currentStock = book == null ? 0 : book.getQuantity();
        this.alertDate = alertDate;
        this.alertType = "Low Stock";
        this.alertStatus = "Open";
        this.message = "Stock level requires attention.";
    }

    public boolean checkStockLevel() {
        if (book == null || minimumStockLevel < 0) {
            return false;
        }

        currentStock = book.getQuantity();

        if (currentStock <= minimumStockLevel) {
            alertStatus = "Open";
            message = "Low stock detected for " + book.getTitle();
            return true;
        }

        alertStatus = "Resolved";
        message = "Stock level is sufficient.";
        return false;
    }

    public void updateMinimumStockLevel(int newLevel) {
        if (newLevel >= 0) {
            minimumStockLevel = newLevel;
            checkStockLevel();
        }
    }

    public boolean resolveAlert() {
        if (book == null || book.getQuantity() <= minimumStockLevel) {
            return false;
        }

        currentStock = book.getQuantity();
        alertStatus = "Resolved";
        message = "Stock shortage resolved.";
        return true;
    }

    public boolean isAlertActive() {
        return alertStatus.equalsIgnoreCase("Open");
    }

    public int calculateStockDeficit() {
        if (book == null) {
            return 0;
        }

        return Math.max(0, minimumStockLevel - book.getQuantity());
    }

    public String determineSeverity() {
        if (book == null) {
            return "Unknown";
        }

        if (book.getQuantity() == 0) {
            return "Critical";
        } else if (book.getQuantity() <= minimumStockLevel / 2) {
            return "High";
        } else if (book.getQuantity() <= minimumStockLevel) {
            return "Medium";
        }

        return "Normal";
    }

    public void updateAlertMessage(String newMessage) {
        if (newMessage != null && !newMessage.trim().isEmpty()) {
            message = newMessage;
        }
    }

    public void displayAlert() {
        System.out.println("Alert ID: " + alertId);
        System.out.println("Book: "
                + (book == null ? "Unknown" : book.getTitle()));
        System.out.println("Minimum Stock: " + minimumStockLevel);
        System.out.println("Current Stock: " + currentStock);
        System.out.println("Alert Date: " + alertDate);
        System.out.println("Alert Type: " + alertType);
        System.out.println("Severity: " + determineSeverity());
        System.out.println("Status: " + alertStatus);
        System.out.println("Message: " + message);
    }

    public String getAlertId() {
        return alertId;
    }

    public String getAlertStatus() {
        return alertStatus;
    }
}
