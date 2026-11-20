
package models;

public class Customer {

    private String customerId;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private int loyaltyPoints;

    public Customer(String customerId, String fullName,
                    String email, String phone, String address) {

        this.customerId = customerId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.loyaltyPoints = 0;
    }

    public void updateContactInfo(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

    public void updateAddress(String newAddress) {
        if (newAddress != null && !newAddress.trim().isEmpty()) {
            address = newAddress;
        }
    }

    public void earnLoyaltyPoints(int points) {
        if (points > 0) {
            loyaltyPoints += points;
        }
    }

    public boolean hasValidEmail() {
        return email != null &&
               email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public void displayCustomer() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + fullName);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Address: " + address);
        System.out.println("Loyalty Points: " + loyaltyPoints);
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }
}

