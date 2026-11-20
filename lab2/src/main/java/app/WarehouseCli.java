package app;

import models.Author;
import models.Book;
import models.BookFormat;
import models.Category;
import models.Customer;
import models.Publisher;
import models.Warehouse;
import services.WarehouseService;

import java.util.Scanner;

/** Console menu; keeps user interaction separate from domain classes. */
public class WarehouseCli {
    private final Scanner scanner;
    private final WarehouseService service;

    public WarehouseCli(Scanner scanner, WarehouseService service) {
        this.scanner = scanner;
        this.service = service;
    }

    public void run() {
        while (processNextChoice()) {
            // Continue until the user selects Exit.
        }
        System.out.println("Goodbye.");
    }

    private boolean processNextChoice() {
        printMenu();
        String choice = scanner.nextLine().trim();
        if ("7".equals(choice)) return false;
        try {
            handleChoice(choice);
        } catch (RuntimeException exception) {
            System.out.println("Operation failed: " + exception.getMessage());
        }
        return true;
    }

    private void handleChoice(String choice) {
        switch (choice) {
            case "1" -> service.getWarehouse().displayAllBooks();
            case "2" -> findBook();
            case "3" -> receiveStock();
            case "4" -> addBook();
            case "5" -> removeBook();
            case "6" -> showInventorySummary();
            default -> System.out.println("Choose an option from 1 to 7.");
        }
    }

    private void printMenu() {
        System.out.println("\n=== BOOK WAREHOUSE MANAGEMENT ===");
        System.out.println("1. Display all books");
        System.out.println("2. Find a book by ISBN");
        System.out.println("3. Receive stock");
        System.out.println("4. Register a book");
        System.out.println("5. Remove a book by ISBN");
        System.out.println("6. Inventory summary");
        System.out.println("7. Exit");
        System.out.print("Select: ");
    }

    private void findBook() {
        System.out.print("ISBN: ");
        Book book = service.findBook(scanner.nextLine().trim());
        book.displayBook();
    }

    private void receiveStock() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Quantity received: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());
        service.receiveStock(isbn, quantity);
        System.out.println("Stock received successfully.");
    }

    private void addBook() {
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Language: ");
        String language = scanner.nextLine().trim();
        System.out.print("Publication year: ");
        int year = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Pages: ");
        int pages = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Initial quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());
        service.registerBook(new Book(title, isbn, language, year, pages, price, quantity));
        System.out.println("Book registered successfully.");
    }

    private void removeBook() {
        System.out.print("ISBN: ");
        service.removeBook(scanner.nextLine().trim());
        System.out.println("Book removed successfully.");
    }

    private void showInventorySummary() {
        System.out.println("Book titles: " + service.titleCount());
        System.out.println("Total copies: " + service.totalCopies());
        System.out.println("Inventory value: " + service.inventoryValue());
    }

    public static WarehouseCli createDemoCli(Scanner scanner) {
        Warehouse warehouse = new Warehouse("Main Book Warehouse", "Minsk, Belarus");
        Book book = new Book("Things Fall Apart", "9780385474542", "English", 1958, 209, 15.50, 20);
        book.assignAuthor(new Author("Chinua Achebe", "Nigerian", 1930, "African novelist"));
        book.assignPublisher(new Publisher("Heinemann", "London", "info@heinemann.com", "123456789", "United Kingdom"));
        book.assignCategory(new Category(1, "African Literature", "African writing"));
        book.assignFormat(new BookFormat("Paperback", 0.35, 20, 13, false));
        warehouse.addBook(book);
        WarehouseService service = new WarehouseService(warehouse);
        service.registerCustomer(new Customer("C001", "Demo Customer", "demo@example.com", "+375291234567", "Minsk"));
        return new WarehouseCli(scanner, service);
    }
}
