
package models;

public class Employee {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String position;
    private double salary;
    private String hireDate;
    private int yearsOfExperience;

    public Employee(String employeeId, String firstName,
                    String lastName, String email,
                    String phone, String position,
                    double salary, String hireDate,
                    int yearsOfExperience) {

        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void updatePosition(String newPosition) {
        if (newPosition != null && !newPosition.trim().isEmpty()) {
            position = newPosition;
        }
    }

    public void updateSalary(double newSalary) {
        if (newSalary > 0) {
            salary = newSalary;
        }
    }

    public void increaseSalary(double percentage) {
        if (percentage > 0) {
            salary += salary * percentage / 100;
        }
    }

    public boolean hasValidEmail() {
        return email != null
                && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public boolean isExperienced() {
        return yearsOfExperience >= 3;
    }

    public double calculateAnnualSalary() {
        return salary * 12;
    }

    public void displayEmployee() {
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Name: " + getFullName());
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Position: " + position);
        System.out.println("Monthly Salary: " + salary);
        System.out.println("Hire Date: " + hireDate);
        System.out.println("Experience: " + yearsOfExperience + " years");
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public double getSalary() {
        return salary;
    }

    public String getPosition() {
        return position;
    }
}
