package services;

import exceptions.BookNotFoundException;
import exceptions.CapacityExceededException;
import exceptions.CustomerNotFoundException;
import exceptions.DeliveryProcessingException;
import exceptions.DuplicateIsbnException;
import exceptions.InsufficientStockException;
import exceptions.InvalidBookDataException;
import exceptions.InvalidOrderStateException;
import exceptions.InventoryAuditException;
import exceptions.PaymentProcessingException;
import exceptions.SupplierNotFoundException;
import models.Book;
import models.Customer;
import models.Delivery;
import models.Order;
import models.OrderItem;
import models.Payment;
import models.Supplier;
import models.Warehouse;
import models.WarehouseSection;

import java.util.LinkedHashMap;
import java.util.Map;

/** Application service that coordinates warehouse domain operations. */
public class WarehouseService {
    private final Warehouse warehouse;
    private final Map<String, Customer> customers = new LinkedHashMap<>();
    private final Map<String, Supplier> suppliers = new LinkedHashMap<>();
    private final Map<String, Order> orders = new LinkedHashMap<>();

    public WarehouseService(Warehouse warehouse) {
        if (warehouse == null) throw new IllegalArgumentException("Warehouse is required.");
        this.warehouse = warehouse;
    }

    public void registerBook(Book book) {
        if (book == null || blank(book.getTitle()) || blank(book.getIsbn()) || book.getPrice() < 0 || book.getQuantity() < 0) {
            throw new InvalidBookDataException("Book data is incomplete or invalid.");
        }
        if (warehouse.findBookByISBN(book.getIsbn()) != null) {
            throw new DuplicateIsbnException("A book with ISBN " + book.getIsbn() + " already exists.");
        }
        warehouse.addBook(book);
    }

    public Book findBook(String isbn) {
        Book book = warehouse.findBookByISBN(isbn);
        if (book == null) throw new BookNotFoundException("No book found for ISBN " + isbn + ".");
        return book;
    }

    public void registerCustomer(Customer customer) {
        if (customer == null || blank(customer.getCustomerId())) throw new IllegalArgumentException("Customer and ID are required.");
        customers.put(customer.getCustomerId(), customer);
    }

    public void registerSupplier(Supplier supplier) {
        if (supplier == null || blank(supplier.getSupplierId())) throw new IllegalArgumentException("Supplier and ID are required.");
        suppliers.put(supplier.getSupplierId(), supplier);
    }

    public Order placeOrder(String orderId, String customerId, String isbn, int quantity, String date) {
        Customer customer = customers.get(customerId);
        if (customer == null) throw new CustomerNotFoundException("Customer " + customerId + " was not registered.");
        Book book = findBook(isbn);
        if (quantity <= 0 || quantity > book.getQuantity()) throw new InsufficientStockException("Requested quantity is not available.");
        if (orders.containsKey(orderId)) throw new InvalidOrderStateException("Order ID already exists.");
        Order order = new Order(orderId, customer, date);
        order.addBook(book, quantity);
        orders.put(orderId, order);
        return order;
    }

    public void removeBook(String isbn) {
        Book book = findBook(isbn);
        warehouse.removeBook(book.getIsbn());
    }

    public void receiveStock(String isbn, int quantity) {
        Book book = findBook(isbn);
        if (quantity <= 0) throw new InsufficientStockException("Stock receipt quantity must be positive.");
        book.addStock(quantity);
    }

    public void processPayment(Payment payment) {
        if (payment == null || !payment.isValidAmount()) throw new PaymentProcessingException("Payment amount or details are invalid.");
        payment.processPayment();
        if (!payment.isPaymentSuccessful()) throw new PaymentProcessingException("Payment could not be completed.");
    }

    public void receiveDelivery(Delivery delivery) {
        if (delivery == null || !delivery.hasValidDetails() || !delivery.receiveDelivery()) {
            throw new DeliveryProcessingException("Delivery cannot be received in its current state.");
        }
    }

    public void addBookToSection(WarehouseSection section, Book book) {
        if (section == null || book == null) throw new InventoryAuditException("Section and book are required.");
        if (section.isFull()) throw new CapacityExceededException("Warehouse section has no free capacity.");
        section.addBook(book);
    }

    public Supplier requireSupplier(String supplierId) {
        Supplier supplier = suppliers.get(supplierId);
        if (supplier == null) throw new SupplierNotFoundException("Supplier " + supplierId + " was not registered.");
        return supplier;
    }

    public double inventoryValue() { return warehouse.calculateInventoryValue(); }
    public int totalCopies() { return warehouse.countTotalCopies(); }
    public int titleCount() { return warehouse.countBookTitles(); }
    public Warehouse getWarehouse() { return warehouse; }

    private boolean blank(String value) { return value == null || value.isBlank(); }
}
