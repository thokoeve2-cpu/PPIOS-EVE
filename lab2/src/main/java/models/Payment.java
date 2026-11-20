
package models;

public class Payment {

    private String paymentId;
    private Order order;
    private Customer customer;
    private double amount;
    private String paymentMethod;
    private String paymentDate;
    private String paymentStatus;
    private String transactionReference;
    private double refundedAmount;

    public Payment(String paymentId, Order order,
                   Customer customer, double amount,
                   String paymentMethod, String paymentDate) {

        this.paymentId = paymentId;
        this.order = order;
        this.customer = customer;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.paymentStatus = "Pending";
        this.transactionReference = "Not assigned";
        this.refundedAmount = 0;
    }

    public void processPayment() {
        if (amount > 0 && paymentStatus.equals("Pending")) {
            paymentStatus = "Paid";
            transactionReference = "TXN-" + paymentId;
            System.out.println("Payment processed successfully.");
        } else {
            System.out.println("Payment cannot be processed.");
        }
    }

    public void refundPayment() {
        refundAmount(amount - refundedAmount);
    }

    public boolean refundAmount(double refundValue) {
        if ((!paymentStatus.equals("Paid") && !paymentStatus.equals("Partially Refunded"))
                || refundValue <= 0 || refundValue > getRemainingAmount()) {
            return false;
        }
        refundedAmount += refundValue;
        paymentStatus = refundedAmount >= amount ? "Refunded" : "Partially Refunded";
        System.out.println("Payment refund recorded successfully.");
        return true;
    }

    public double getRemainingAmount() {
        return Math.max(0, amount - refundedAmount);
    }

    public double getRefundedAmount() {
        return refundedAmount;
    }

    public void changePaymentMethod(String newMethod) {
        if (newMethod != null && !newMethod.trim().isEmpty()
                && paymentStatus.equals("Pending")) {
            paymentMethod = newMethod;
        }
    }

    public boolean isPaymentSuccessful() {
        return paymentStatus.equals("Paid") || paymentStatus.equals("Partially Refunded");
    }

    public boolean isValidAmount() {
        return amount > 0;
    }

    public void displayPayment() {
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Customer: " + customer.getFullName());
        System.out.println("Amount: " + amount);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Payment Date: " + paymentDate);
        System.out.println("Payment Status: " + paymentStatus);
        System.out.println("Transaction Reference: " + transactionReference);
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public double getAmount() {
        return amount;
    }
}
