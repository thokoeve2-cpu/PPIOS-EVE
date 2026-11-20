package models;

import java.util.ArrayList;
import java.util.List;

public class ProcurementPlan {
    private String planId;
    private Supplier supplier;
    private Employee preparedBy;
    private String planningPeriod;
    private double budget;
    private String status;
    private final List<RestockRequest> requests=new ArrayList<>();

    public ProcurementPlan(String id,Supplier supplier,Employee preparedBy,String period,double budget) {
        this.planId=id;
        this.supplier=supplier;
        this.preparedBy=preparedBy;
        this.planningPeriod=period;
        this.budget=budget;
        this.status="Draft";
    }
    public boolean addRequest(RestockRequest request) {
        if(request==null || !"Draft".equals(status) || requests.contains(request)) return false;
        requests.add(request);
        return true;
    }
    public double calculateEstimatedSpend() { return requests.stream().mapToDouble(RestockRequest::calculateEstimatedCost).sum(); }
    public boolean isWithinBudget() { return calculateEstimatedSpend()<=budget; }
    public boolean approvePlan() {
        if(requests.isEmpty() || !isWithinBudget() || !"Draft".equals(status)) return false;
        status="Approved";
        return true;
    }
    public boolean cancelPlan() {
        if("Approved".equals(status)) return false;
        status="Cancelled";
        return true;
    }
    public int countRequests() { return requests.size(); }
    public String getStatus() { return status; }
}
