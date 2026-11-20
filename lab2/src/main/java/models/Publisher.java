
package models;

public class Publisher {

    private String name;
    private String address;
    private String email;
    private String phone;
    private String country;

    public Publisher(String name, String address,
                     String email, String phone,
                     String country) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.country = country;
    }

    public void updateEmail(String newEmail) {
        email = newEmail;
    }

    public void updateAddress(String newAddress) {
        address = newAddress;
    }

    public void updatePhone(String newPhone) {
        phone = newPhone;
    }

    public boolean hasValidEmail() {
        return email != null && email.contains("@");
    }

    public void displayPublisher() {
        System.out.println("Publisher: " + name);
        System.out.println("Address: " + address);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Country: " + country);
    }
    public String getName() {
    return name;
}
}

