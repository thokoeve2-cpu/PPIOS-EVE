package models;

public class EmployeeShift {
    private String shiftId;
    private Employee employee;
    private String shiftDate;
    private String startTime;
    private String endTime;
    private String role;
    private String status;

    public EmployeeShift(String id,Employee employee,String date,String start,String end,String role) {
        this.shiftId=id;
        this.employee=employee;
        this.shiftDate=date;
        this.startTime=start;
        this.endTime=end;
        this.role=role;
        this.status="Scheduled";
    }
    public boolean validateTimes() { return startTime!=null && endTime!=null && startTime.compareTo(endTime)<0; }
    public boolean startShift(String time) {
        if(!"Scheduled".equals(status) || !validateTimes() || time==null) return false;
        status="In Progress";
        return true;
    }
    public boolean finishShift() {
        if(!"In Progress".equals(status)) return false;
        status="Completed";
        return true;
    }
    public boolean cancelShift() {
        if("Completed".equals(status)) return false;
        status="Cancelled";
        return true;
    }
    public boolean isAssignedTo(Employee candidate) { return employee==candidate; }
    public String getStatus() { return status; }
}
