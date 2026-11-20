
package models;

import java.util.ArrayList;

public class InventoryReport {

    private String reportId;
    private Warehouse warehouse;
    private String generatedDate;
    private String reportType;
    private String notes;

    public InventoryReport(String reportId, Warehouse warehouse,
                           String generatedDate, String reportType) {
        this.reportId = reportId;
        this.warehouse = warehouse;
        this.generatedDate = generatedDate;
        this.reportType = reportType;
        this.notes = "No additional notes.";
    }

    public void generateSummary() {
        System.out.println("=== INVENTORY SUMMARY REPORT ===");
        System.out.println("Report ID: " + reportId);
        System.out.println("Generated Date: " + generatedDate);
        System.out.println("Report Type: " + reportType);
        System.out.println("Warehouse Book Titles: "
                + warehouse.countBookTitles());
        System.out.println("Total Copies: "
                + warehouse.countTotalCopies());
        System.out.println("Inventory Value: "
                + warehouse.calculateInventoryValue());
    }

    public double calculateAverageStock() {
        int titles = warehouse.countBookTitles();

        if (titles == 0) {
            return 0;
        }

        return (double) warehouse.countTotalCopies() / titles;
    }

    public double calculateAverageBookValue() {
        int titles = warehouse.countBookTitles();

        if (titles == 0) {
            return 0;
        }

        return warehouse.calculateInventoryValue() / titles;
    }

    public boolean isBookLowStock(Book book, int minimumStock) {
        return book != null
                && minimumStock >= 0
                && book.getQuantity() <= minimumStock;
    }

    public int countLowStockBooks(ArrayList<Book> books,
                                  int minimumStock) {
        if (books == null || minimumStock < 0) {
            return 0;
        }

        int count = 0;

        for (Book book : books) {
            if (isBookLowStock(book, minimumStock)) {
                count++;
            }
        }

        return count;
    }

    public String assessInventoryValue() {
        double value = warehouse.calculateInventoryValue();

        if (value == 0) {
            return "Empty inventory";
        } else if (value < 1000) {
            return "Low inventory value";
        } else if (value < 5000) {
            return "Moderate inventory value";
        } else {
            return "High inventory value";
        }
    }

    public void updateNotes(String newNotes) {
        if (newNotes != null && !newNotes.trim().isEmpty()) {
            notes = newNotes;
        }
    }

    public String createReportTitle() {
        return reportType + " Report - " + reportId;
    }

    public void displayReportDetails() {
        System.out.println("Report Title: " + createReportTitle());
        System.out.println("Generated Date: " + generatedDate);
        System.out.println("Notes: " + notes);
        System.out.println("Inventory Assessment: "
                + assessInventoryValue());
    }

    public String getReportId() {
        return reportId;
    }
}
