package models;

public class Coupon {
    private String code;
    private double discountAmount;
    private double minimumPurchase;
    private int usageLimit;
    private int usageCount;
    private String expiryDate;
    private boolean enabled;

    public Coupon(String code, double discountAmount, double minimumPurchase,
                  int usageLimit, String expiryDate) {
        this.code = code;
        this.discountAmount = discountAmount;
        this.minimumPurchase = minimumPurchase;
        this.usageLimit = usageLimit;
        this.expiryDate = expiryDate;
        this.enabled = true;
    }

    public boolean isValid(String date, double basketTotal) {
        return enabled && date != null && date.compareTo(expiryDate) <= 0
                && basketTotal >= minimumPurchase && usageCount < usageLimit;
    }

    public double apply(double basketTotal, String date) {
        if (!isValid(date, basketTotal)) return basketTotal;
        usageCount++;
        return Math.max(0, basketTotal - discountAmount);
    }

    public void disable() { enabled = false; }

    public boolean extendExpiry(String date) {
        if (date == null || date.compareTo(expiryDate) < 0) return false;
        expiryDate = date;
        return true;
    }

    public int remainingUses() { return Math.max(0, usageLimit - usageCount); }
    public String getCode() { return code; }
}
