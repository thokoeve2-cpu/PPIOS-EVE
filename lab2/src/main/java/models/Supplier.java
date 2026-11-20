package models;

import java.util.ArrayList;

public class Supplier {

    private String supplierId;
    private String companyName;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
    private String country;

    private ArrayList<Book> suppliedBooks;

    public Supplier(String supplierId, String companyName,
                    String contactPerson, String email,
                    String phone, String address, String country) {

        this.supplierId = supplierId;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.country = country;

        this.suppliedBooks = new ArrayList<>();
    }

    public void addSuppliedBook(Book book) {
        if (book != null) {
            suppliedBooks.add(book);
            System.out.println("Book registered from supplier.");
        }
    }

    public void removeSuppliedBook(Book book) {
        if (suppliedBooks.remove(book)) {
            System.out.println("Book removed from supplier records.");
        }
    }

    public int countSuppliedBooks() {
        return suppliedBooks.size();
    }

    public void updateEmail(String newEmail) {
        if (newEmail != null && newEmail.contains("@")) {
            email = newEmail;
        }
    }

    public void updatePhone(String newPhone) {
        if (newPhone != null && !newPhone.trim().isEmpty()) {
            phone = newPhone;
        }
    }

    public void updateAddress(String newAddress) {
        if (newAddress != null && !newAddress.trim().isEmpty()) {
            address = newAddress;
        }
    }

    public boolean hasValidEmail() {
        return email != null
                && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public double calculateSuppliedBooksValue() {
        double total = 0;

        for (Book book : suppliedBooks) {
            total += book.calculateTotalValue();
        }

        return total;
    }

    public void displaySupplier() {
        System.out.println("Supplier ID: " + supplierId);
        System.out.println("Company: " + companyName);
        System.out.println("Contact Person: " + contactPerson);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Address: " + address);
        System.out.println("Country: " + country);
        System.out.println("Supplied Book Titles: " + countSuppliedBooks());
    }

    public String getSupplierId() {
        return supplierId;
    }

    public String getCompanyName() {
        return companyName;
    }
}
