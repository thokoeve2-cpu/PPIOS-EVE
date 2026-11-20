package models;

import exceptions.InventoryAuditException;

import java.util.ArrayList;
import java.util.List;

public class InventoryAudit {
    private String auditId;
    private Warehouse warehouse;
    private Employee auditor;
    private String auditDate;
    private final List<Book> auditedBooks = new ArrayList<>();
    private final List<Integer> countedQuantities = new ArrayList<>();
    private String auditStatus;
    private int totalDiscrepancies;

    public InventoryAudit(String auditId, Warehouse warehouse, Employee auditor, String auditDate) {
        this.auditId = auditId;
        this.warehouse = warehouse;
        this.auditor = auditor;
        this.auditDate = auditDate;
        this.auditStatus = "In Progress";
        this.totalDiscrepancies = 0;
    }

    public boolean recordPhysicalCount(Book book, int actualQuantity) {
        if (book == null || actualQuantity < 0 || !"In Progress".equals(auditStatus)) return false;
        int index = auditedBooks.indexOf(book);
        if (index >= 0) countedQuantities.set(index, actualQuantity);
        else { auditedBooks.add(book); countedQuantities.add(actualQuantity); }
        return true;
    }

    public int calculateBookDiscrepancy(Book book) {
        int index = auditedBooks.indexOf(book);
        return index < 0 ? 0 : countedQuantities.get(index) - book.getQuantity();
    }

    public void calculateTotalDiscrepancies() {
        totalDiscrepancies = 0;
        for (Book book : auditedBooks) if (calculateBookDiscrepancy(book) != 0) totalDiscrepancies++;
    }

    public boolean applyStockCorrections() {
        if (!"In Progress".equals(auditStatus) || auditedBooks.isEmpty()) return false;
        for (int i = 0; i < auditedBooks.size(); i++) {
            Book book = auditedBooks.get(i);
            int difference = calculateBookDiscrepancy(book);
            if (difference > 0) book.addStock(difference);
            else if (difference < 0) book.removeStock(-difference);
        }
        calculateTotalDiscrepancies();
        auditStatus = "Completed";
        return true;
    }

    public boolean reopen() {
        if (!"Completed".equals(auditStatus)) return false;
        auditStatus = "In Progress";
        return true;
    }

    public boolean validateAudit() {
        return auditId != null && !auditId.isBlank() && warehouse != null && auditor != null && auditDate != null;
    }

    public void completeWithoutCorrections() {
        if (auditedBooks.isEmpty()) throw new InventoryAuditException("An audit must contain at least one physical count.");
        calculateTotalDiscrepancies();
        auditStatus = "Completed";
    }

    public int countAuditedBooks() { return auditedBooks.size(); }
    public boolean isAuditComplete() { return "Completed".equals(auditStatus); }
    public void displayAudit() {
        System.out.println("Audit ID: " + auditId);
        System.out.println("Warehouse: " + warehouse);
        System.out.println("Auditor: " + auditor.getFullName());
        System.out.println("Audit Date: " + auditDate);
        System.out.println("Books Audited: " + countAuditedBooks());
        System.out.println("Discrepancies: " + totalDiscrepancies);
        System.out.println("Audit Status: " + auditStatus);
    }
    public String getAuditStatus() { return auditStatus; }
    public int getTotalDiscrepancies() { return totalDiscrepancies; }
}
