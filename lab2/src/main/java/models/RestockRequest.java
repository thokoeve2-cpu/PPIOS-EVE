
package models;

public class RestockRequest {

    private String requestId;
    private Book book;
    private Supplier supplier;
    private Employee requestedBy;
    private int requestedQuantity;
    private String requestDate;
    private String priority;
    private String status;
    private String reason;

    public RestockRequest(String requestId, Book book,
                          Supplier supplier, Employee requestedBy,
                          int requestedQuantity, String requestDate,
                          String priority, String reason) {

        this.requestId = requestId;
        this.book = book;
        this.supplier = supplier;
        this.requestedBy = requestedBy;
        this.requestedQuantity = requestedQuantity;
        this.requestDate = requestDate;
        this.priority = priority;
        this.reason = reason;
        this.status = "Pending";
    }

    public boolean isValidRequest() {
        return requestId != null
                && !requestId.trim().isEmpty()
                && book != null
                && supplier != null
                && requestedBy != null
                && requestedQuantity > 0
                && requestDate != null;
    }

    public boolean approveRequest() {
        if (!isValidRequest()
                || !status.equalsIgnoreCase("Pending")) {
            return false;
        }

        status = "Approved";
        return true;
    }

    public boolean rejectRequest(String rejectionReason) {
        if (!status.equalsIgnoreCase("Pending")
                || rejectionReason == null
                || rejectionReason.trim().isEmpty()) {
            return false;
        }

        reason = rejectionReason;
        status = "Rejected";
        return true;
    }

    public boolean cancelRequest() {
        if (status.equalsIgnoreCase("Ordered")
                || status.equalsIgnoreCase("Cancelled")) {
            return false;
        }

        status = "Cancelled";
        return true;
    }

    public double calculateEstimatedCost() {
        return book == null
                ? 0
                : book.getPrice() * requestedQuantity;
    }

    public boolean isUrgent() {
        return priority != null
                && priority.equalsIgnoreCase("High");
    }

    public void updateRequestedQuantity(int newQuantity) {
        if (newQuantity > 0
                && status.equalsIgnoreCase("Pending")) {
            requestedQuantity = newQuantity;
        }
    }

    public PurchaseOrder createPurchaseOrder(
            String purchaseOrderId,
            String orderDate,
            String expectedDeliveryDate) {

        if (!status.equalsIgnoreCase("Approved")) {
            return null;
        }

        PurchaseOrder order = new PurchaseOrder(
                purchaseOrderId,
                supplier,
                orderDate,
                expectedDeliveryDate
        );

        if (order.addBook(book, requestedQuantity)) {
            status = "Ordered";
            return order;
        }

        return null;
    }

    public void displayRequest() {
        System.out.println("Request ID: " + requestId);
        System.out.println("Book: " + book.getTitle());
        System.out.println("Supplier: " + supplier.getCompanyName());
        System.out.println("Requested By: " + requestedBy.getFullName());
        System.out.println("Quantity: " + requestedQuantity);
        System.out.println("Request Date: " + requestDate);
        System.out.println("Priority: " + priority);
        System.out.println("Status: " + status);
        System.out.println("Reason: " + reason);
        System.out.println("Estimated Cost: " + calculateEstimatedCost());
    }

    public String getRequestId() {
        return requestId;
    }

    public String getStatus() {
        return status;
    }
}
