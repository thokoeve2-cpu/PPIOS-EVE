package models;

public class Refund {
    private String refundId;
    private Payment payment;
    private ReturnRequest returnRequest;
    private double amount;
    private String refundDate;
    private String method;
    private String status;

    public Refund(String refundId, Payment payment, ReturnRequest request,
                  double amount, String refundDate, String method) {
        this.refundId = refundId;
        this.payment = payment;
        this.returnRequest = request;
        this.amount = amount;
        this.refundDate = refundDate;
        this.method = method;
        this.status = "Pending";
    }

    public boolean validate() {
        return payment != null && returnRequest != null && amount > 0
                && amount <= payment.getRemainingAmount()
                && returnRequest.getStatus().equals("Received");
    }

    public boolean process() {
        if (!validate() || !"Pending".equals(status)) return false;
        if (!payment.refundAmount(amount)) return false;
        status = "Processed";
        return true;
    }

    public boolean cancel() {
        if (!"Pending".equals(status)) return false;
        status = "Cancelled";
        return true;
    }

    public double calculateRemainingPayment() {
        return payment == null ? 0 : Math.max(0, payment.getRemainingAmount() - amount);
    }

    public boolean usesOriginalMethod() {
        return method != null && method.equalsIgnoreCase("Original");
    }

    public String getStatus() { return status; }
}
