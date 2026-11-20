package models;

public class Discount {
    private String discountId;
    private String name;
    private double percentage;
    private String startDate;
    private String endDate;
    private boolean active;
    private String categoryName;

    public Discount(String id, String name, double percentage,
                    String startDate, String endDate, String categoryName) {
        this.discountId = id;
        this.name = name;
        this.percentage = percentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.categoryName = categoryName;
        this.active = true;
    }

    public boolean activate() {
        if (percentage <= 0 || percentage > 100) return false;
        active = true;
        return true;
    }

    public void deactivate() { active = false; }

    public boolean isApplicable(String date, Category category) {
        boolean categoryMatches = categoryName == null
                || (category != null && categoryName.equalsIgnoreCase(category.getCategoryName()));
        return active && date != null && date.compareTo(startDate) >= 0
                && date.compareTo(endDate) <= 0 && categoryMatches;
    }

    public double calculateDiscount(double price) {
        return active && price > 0 ? price * percentage / 100.0 : 0;
    }

    public double applyToPrice(double price) {
        return Math.max(0, price - calculateDiscount(price));
    }

    public boolean changePercentage(double value) {
        if (value < 0 || value > 100) return false;
        percentage = value;
        return true;
    }

    public String getDiscountId() { return discountId; }
}
