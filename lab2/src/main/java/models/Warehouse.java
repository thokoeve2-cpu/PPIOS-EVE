
package models;

import java.util.ArrayList;

public class Warehouse {

    private String warehouseName;
    private String location;
    private ArrayList<Book> books;

    public Warehouse(String warehouseName, String location) {
        this.warehouseName = warehouseName;
        this.location = location;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        if (book == null || findBookByISBN(book.getIsbn()) != null) {
            System.out.println("Book cannot be added.");
            return;
        }

        books.add(book);
        System.out.println("Book added successfully.");
    }

    public void removeBook(String isbn) {
        Book book = findBookByISBN(isbn);

        if (book != null) {
            books.remove(book);
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    public Book findBookByISBN(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }

        return null;
    }

    public Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }

        return null;
    }

    public void displayAllBooks() {
        System.out.println("WAREHOUSE: " + warehouseName);
        System.out.println("LOCATION: " + location);

        for (Book book : books) {
            System.out.println("--------------------");
            book.displayBook();
        }
    }

    public double calculateInventoryValue() {
        double total = 0;

        for (Book book : books) {
            total += book.calculateTotalValue();
        }

        return total;
    }

    public int countTotalCopies() {
        int total = 0;

        for (Book book : books) {
            total += book.getQuantity();
        }

        return total;
    }

    public int countBookTitles() {
        return books.size();
    }
}

