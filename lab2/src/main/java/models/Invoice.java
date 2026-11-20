package models;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private String invoiceId;
    private Order order;
    private Customer customer;
    private String issueDate;
    private String dueDate;
    private String status;
    private double taxRate;
    private double discountAmount;
    private final List<InvoiceLine> lines = new ArrayList<>();

    public Invoice(String invoiceId, Order order, Customer customer, String issueDate, String dueDate, double taxRate) {
        this.invoiceId = invoiceId; this.order = order; this.customer = customer;
        this.issueDate = issueDate; this.dueDate = dueDate; this.taxRate = Math.max(0, taxRate); this.status = "Draft";
    }
    public boolean addLine(InvoiceLine line) {
        if (line == null || !"Draft".equals(status)) return false;
        lines.add(line);
        return true;
    }
    public double calculateSubtotal() { return lines.stream().mapToDouble(InvoiceLine::calculateNet).sum(); }
    public double calculateTax() { return calculateSubtotal() * taxRate / 100.0; }
    public double calculateTotal() { return Math.max(0, calculateSubtotal() + calculateTax() - discountAmount); }
    public boolean applyDiscount(double amount) {
        if (amount < 0 || amount > calculateSubtotal() + calculateTax()) return false;
        discountAmount = amount;
        return true;
    }
    public boolean issue() {
        if (lines.isEmpty() || !"Draft".equals(status)) return false;
        status = "Issued";
        return true;
    }
    public boolean markPaid() {
        if (!"Issued".equals(status)) return false;
        status = "Paid";
        return true;
    }
    public boolean cancel() {
        if ("Paid".equals(status) || "Cancelled".equals(status)) return false;
        status = "Cancelled";
        return true;
    }
    public boolean isOverdue(String today) { return "Issued".equals(status) && dueDate != null && today != null &&
        today.compareTo(dueDate) > 0; }
    public void displayInvoice() { System.out.println("Invoice " + invoiceId + " for " + customer.getFullName() + ": " +
        calculateTotal() + " [" + status + "]"); }
    public String getInvoiceId() { return invoiceId; }
    public String getStatus() { return status; }
}
