package models;

public class AttendanceRecord {
    private String recordId;
    private Employee employee;
    private EmployeeShift shift;
    private String clockIn;
    private String clockOut;
    private int breakMinutes;
    private String attendanceStatus;

    public AttendanceRecord(String id,Employee employee,EmployeeShift shift) {
        this.recordId=id;
        this.employee=employee;
        this.shift=shift;
        this.breakMinutes=0;
        this.attendanceStatus="Absent";
    }
    public boolean clockIn(String time) {
        if(time==null || time.isBlank() || clockIn!=null) return false;
        clockIn=time;
        attendanceStatus="Present";
        return true;
    }
    public boolean clockOut(String time) {
        if(clockIn==null || time==null || time.compareTo(clockIn)<=0 || clockOut!=null) return false;
        clockOut=time;
        return true;
    }
    public boolean recordBreak(int minutes) {
        if(minutes<0 || minutes>240) return false;
        breakMinutes=minutes;
        return true;
    }
    public int calculateWorkedMinutes() {
        if(clockIn==null || clockOut==null) return 0;
        return Math.max(0,minutesBetween(clockIn,clockOut)-breakMinutes);
    }
    private int minutesBetween(String start,String end) {
        String[] a=start.split(":");
        String[] b=end.split(":");
        return (Integer.parseInt(b[0])*60+Integer.parseInt(b[1]))-(Integer.parseInt(a[0])*60+Integer.parseInt(a[1]));
    }
    public boolean isLate(String scheduledStart) { return clockIn!=null && scheduledStart!=null &&
        clockIn.compareTo(scheduledStart)>0; }
    public Employee getEmployee() { return employee; }
}
