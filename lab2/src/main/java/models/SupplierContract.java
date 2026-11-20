package models;

public class SupplierContract {
    private String contractId;
    private Supplier supplier;
    private String startDate;
    private String endDate;
    private double discountPercentage;
    private double spendingLimit;
    private double currentSpending;
    private String status;

    public SupplierContract(String id,Supplier supplier,String startDate,String endDate,double discount,double limit) {
        this.contractId=id;
        this.supplier=supplier;
        this.startDate=startDate;
        this.endDate=endDate;
        this.discountPercentage=discount;
        this.spendingLimit=limit;
        this.status="Active";
    }
    public boolean isValidOn(String date) { return "Active".equals(status) && date!=null && date.compareTo(startDate)>=0 &&
        date.compareTo(endDate)<=0; }
    public boolean recordPurchase(double amount,String date) {
        if(amount<=0 || !isValidOn(date) || currentSpending+amount>spendingLimit) return false;
        currentSpending+=amount;
        return true;
    }
    public double applySupplierDiscount(double amount) { return amount>0 ? amount*(1-discountPercentage/100.0) : 0; }
    public void suspend() { if("Active".equals(status)) status="Suspended"; }
    public boolean renew(String newEndDate) {
        if(newEndDate==null || newEndDate.compareTo(endDate)<=0) return false;
        endDate=newEndDate;
        status="Active";
        return true;
    }
    public double remainingLimit() { return Math.max(0,spendingLimit-currentSpending); }
    public Supplier getSupplier() { return supplier; }
}
