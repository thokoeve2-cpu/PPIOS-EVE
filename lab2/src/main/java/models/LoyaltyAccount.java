package models;

public class LoyaltyAccount {
    private String accountId;
    private Customer customer;
    private int pointsBalance;
    private int lifetimePoints;
    private String tier;
    private String lastActivityDate;

    public LoyaltyAccount(String accountId, Customer customer, String date) {
        this.accountId=accountId;
        this.customer=customer;
        this.lastActivityDate=date;
        this.pointsBalance=0;
        this.lifetimePoints=0;
        this.tier="Bronze";
    }
    public boolean earnPoints(double purchaseAmount) {
        if(purchaseAmount<=0) return false;
        int earned=(int)Math.floor(purchaseAmount);
        pointsBalance+=earned;
        lifetimePoints+=earned;
        updateTier();
        return true;
    }
    public boolean redeemPoints(int points) {
        if(points<=0 || points>pointsBalance) return false;
        pointsBalance-=points;
        return true;
    }
    public double calculateRewardValue(int pointsPerCurrencyUnit) { return pointsPerCurrencyUnit<=0 ? 0 :
        (double)pointsBalance/pointsPerCurrencyUnit; }
    public void expirePoints(int points) { if(points>0) pointsBalance=Math.max(0,pointsBalance-points); }
    public void recordActivity(String date) { if(date!=null && !date.isBlank()) lastActivityDate=date; }
    private void updateTier() { tier=lifetimePoints>=1000?"Gold":lifetimePoints>=300?"Silver":"Bronze"; }
    public String getTier() { return tier; }
    public int getPointsBalance() { return pointsBalance; }
}
