
package models;

public class Delivery {

    private String deliveryId;
    private Supplier supplier;
    private Employee employee;
    private PurchaseOrder purchaseOrder;
    private String deliveryDate;
    private String deliveryStatus;
    private String trackingNumber;
    private String notes;

    public Delivery(String deliveryId, Supplier supplier,
                    Employee employee, PurchaseOrder purchaseOrder,
                    String deliveryDate, String trackingNumber) {

        this.deliveryId = deliveryId;
        this.supplier = supplier;
        this.employee = employee;
        this.purchaseOrder = purchaseOrder;
        this.deliveryDate = deliveryDate;
        this.trackingNumber = trackingNumber;
        this.deliveryStatus = "Pending";
        this.notes = "No additional notes.";
    }

    public boolean receiveDelivery() {
        if (purchaseOrder == null
                || !purchaseOrder.getStatus().equalsIgnoreCase("Confirmed")
                || !deliveryStatus.equalsIgnoreCase("Pending")) {
            return false;
        }

        if (purchaseOrder.markAsDelivered()) {
            deliveryStatus = "Received";
            return true;
        }

        return false;
    }

    public boolean rejectDelivery(String reason) {
        if (!deliveryStatus.equalsIgnoreCase("Pending")
                || reason == null || reason.trim().isEmpty()) {
            return false;
        }

        deliveryStatus = "Rejected";
        notes = reason;
        return true;
    }

    public boolean isDeliveryComplete() {
        return deliveryStatus.equalsIgnoreCase("Received");
    }

    public void updateTrackingNumber(String newNumber) {
        if (newNumber != null && !newNumber.trim().isEmpty()
                && deliveryStatus.equalsIgnoreCase("Pending")) {
            trackingNumber = newNumber;
        }
    }

    public void updateDeliveryDate(String newDate) {
        if (newDate != null && !newDate.trim().isEmpty()
                && deliveryStatus.equalsIgnoreCase("Pending")) {
            deliveryDate = newDate;
        }
    }

    public void addDeliveryNotes(String newNotes) {
        if (newNotes != null && !newNotes.trim().isEmpty()) {
            notes = newNotes;
        }
    }

    public boolean hasValidDetails() {
        return deliveryId != null
                && !deliveryId.trim().isEmpty()
                && supplier != null
                && employee != null
                && purchaseOrder != null
                && deliveryDate != null
                && trackingNumber != null
                && !trackingNumber.trim().isEmpty();
    }

    public void displayDelivery() {
        System.out.println("Delivery ID: " + deliveryId);
        System.out.println("Supplier: " + supplier.getCompanyName());
        System.out.println("Employee: " + employee.getFullName());
        System.out.println("Purchase Order: "
                + purchaseOrder.getPurchaseOrderId());
        System.out.println("Delivery Date: " + deliveryDate);
        System.out.println("Tracking Number: " + trackingNumber);
        System.out.println("Delivery Status: " + deliveryStatus);
        System.out.println("Notes: " + notes);
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }
}

