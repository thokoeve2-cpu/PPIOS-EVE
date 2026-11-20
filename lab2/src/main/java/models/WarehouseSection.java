
package models;

import java.util.ArrayList;

public class WarehouseSection {

    private String sectionId;
    private String sectionName;
    private String locationCode;
    private int maximumCapacity;
    private ArrayList<Book> books;

    public WarehouseSection(String sectionId, String sectionName,
                            String locationCode, int maximumCapacity) {

        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.locationCode = locationCode;
        this.maximumCapacity = maximumCapacity;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        if (book == null) {
            System.out.println("Invalid book.");
            return;
        }

        if (books.size() >= maximumCapacity) {
            System.out.println("Warehouse section is full.");
            return;
        }

        books.add(book);
        System.out.println("Book added to warehouse section.");
    }

    public void removeBook(Book book) {
        if (books.remove(book)) {
            System.out.println("Book removed from warehouse section.");
        }
    }

    public int countBooks() {
        return books.size();
    }

    public boolean isFull() {
        return books.size() >= maximumCapacity;
    }

    public int getAvailableCapacity() {
        return maximumCapacity - books.size();
    }

    public boolean containsBook(Book book) {
        return books.contains(book);
    }

    public void renameSection(String newName) {
        if (newName != null && !newName.trim().isEmpty()) {
            sectionName = newName;
        }
    }

    public void displaySection() {
        System.out.println("Section ID: " + sectionId);
        System.out.println("Section Name: " + sectionName);
        System.out.println("Location Code: " + locationCode);
        System.out.println("Maximum Capacity: " + maximumCapacity);
        System.out.println("Books Stored: " + countBooks());
        System.out.println("Available Capacity: " + getAvailableCapacity());
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getSectionId() {
        return sectionId;
    }
}
