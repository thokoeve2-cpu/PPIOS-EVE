package models;

public class BookReservation {

    private String reservationId;
    private Book book;
    private Customer customer;
    private int quantity;
    private String reservationDate;
    private String expiryDate;
    private String status;

    public BookReservation(String reservationId, Book book,
                           Customer customer, int quantity,
                           String reservationDate, String expiryDate) {

        this.reservationId = reservationId;
        this.book = book;
        this.customer = customer;
        this.quantity = quantity;
        this.reservationDate = reservationDate;
        this.expiryDate = expiryDate;
        this.status = "Active";
    }

    public void cancelReservation() {
        if (status.equals("Active")) {
            status = "Cancelled";
            System.out.println("Reservation cancelled.");
        }
    }

    public void completeReservation() {
        if (status.equals("Active")) {
            status = "Completed";
            System.out.println("Reservation completed.");
        }
    }

    public void extendReservation(String newExpiryDate) {
        if (status.equals("Active") &&
            newExpiryDate != null &&
            !newExpiryDate.trim().isEmpty()) {

            expiryDate = newExpiryDate;
        }
    }

    public boolean isActive() {
        return status.equals("Active");
    }

    public boolean isValidQuantity() {
        return quantity > 0;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Book: " + book.getTitle());
        System.out.println("Customer: " + customer.getFullName());
        System.out.println("Quantity: " + quantity);
        System.out.println("Reservation Date: " + reservationDate);
        System.out.println("Expiry Date: " + expiryDate);
        System.out.println("Status: " + status);
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getStatus() {
        return status;
    }
}
