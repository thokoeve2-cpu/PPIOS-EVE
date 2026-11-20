# Book Warehouse Management System — Laboratory Work 2

**Course:** Design of Intelligent Systems Software  
**Language:** Java 17+  
**Domain:** Book warehouse inventory, procurement, sales, delivery and staff operations

## Project overview

This project models the operation of a book warehouse. It includes book cataloguing, customers, suppliers, stock movements, reservations, purchase orders, deliveries, invoices, refunds, staff scheduling, inventory audits and reporting. Domain classes are kept in `models`, orchestration and validation are in `services`, and the interactive console is in `app`.

## Quantitative requirements audit

Counts are generated from the Java source. Domain behaviours exclude constructors, getters, setters and display-only methods; predicates and calculations are included. Associations count distinct model-to-model relationships represented by fields or public method parameters.

| Metric | Current count | Requirement | Status |
|---|---:|---:|---|
| Production classes (models + exceptions) | 51 | 50 | PASS |
| Declared private fields (all production classes) | 287 | 150 | PASS |
| Public model behaviours (source-level count) | 226 | 100 | PASS |
| Distinct model associations | 63 | 30 | PASS |
| Custom exception classes | 12 | 12 | PASS |

**Note:** these are source-level structural counts. The instructor still determines whether each operation is sufficiently meaningful. The CI workflow is configured to enforce a 90% JaCoCo line-coverage threshold.

## Build and run from the console

Prerequisite: JDK 17 or newer. `mvnw` uses an existing Maven installation or downloads Apache Maven 3.9.9 to the local Maven cache on first use. An internet connection is required for the first Maven and dependency download.

```bash
chmod +x mvnw build.sh run.sh test.sh lint.sh
./build.sh
./run.sh
./test.sh
./lint.sh
```

The runnable JAR is created at `target/book-warehouse-lab2-1.0.0.jar`. You can also run `mvn clean verify` directly when Maven is installed.

## CLI menu

The CLI is implemented in `app.WarehouseCli` and is separate from the domain model. It supports:

1. Display all books.
2. Find a book by ISBN.
3. Receive stock.
4. Register a book.
5. Remove a book by ISBN.
6. Display inventory totals and value.
7. Exit.

## Source structure

```text
src/main/java/
  Main.java
  app/WarehouseCli.java
  services/WarehouseService.java
  models/       # 39 domain model classes
  exceptions/   # 12 custom exception classes
src/test/java/
  ProjectTest.java
```

## Class reference

Every class below lists its declared fields, public methods/constructors and direct associations.

### `Main`
- **File:** `Main.java`
- **Fields (0):** None
- **Public methods and constructors (1):** `static void main(String[] args)`
- **Associations:** None

### `WarehouseCli`
- **File:** `app/WarehouseCli.java`
- **Fields (2):** `final Scanner scanner`, `final WarehouseService service`
- **Public methods and constructors (3):** `WarehouseCli(Scanner scanner, WarehouseService service)`; `void run()`; `static WarehouseCli createDemoCli(Scanner scanner)`
- **Associations:** `WarehouseService`

### `BookNotFoundException`
- **File:** `exceptions/BookNotFoundException.java`
- **Fields (1):** `static final long serialVersionUID = 1L`
- **Public methods and constructors (2):** `BookNotFoundException(String message)`; `BookNotFoundException(String message, Throwable cause)`
- **Associations:** None

### `BookWarehouseException`
- **File:** `exceptions/BookWarehouseException.java`
- **Fields (1):** `static final long serialVersionUID = 1L`
- **Public methods and constructors (2):** `BookWarehouseException(String message)`; `BookWarehouseException(String message, Throwable cause)`
- **Associations:** None

### `CapacityExceededException`
- **File:** `exceptions/CapacityExceededException.java`
- **Fields (1):** `static final long serialVersionUID = 1L`
- **Public methods and constructors (2):** `CapacityExceededException(String message)`; `CapacityExceededException(String message, Throwable cause)`
- **Associations:** None

### `CustomerNotFoundException`
- **File:** `exceptions/CustomerNotFoundException.java`
- **Fields (1):** `static final long serialVersionUID = 1L`
- **Public methods and constructors (2):** `CustomerNotFoundException(String message)`; `CustomerNotFoundException(String message, Throwable cause)`
- **Associations:** None

### `DeliveryProcessingException`
- **File:** `exceptions/DeliveryProcessingException.java`
- **Fields (1):** `static final long serialVersionUID = 1L`
- **Public methods and constructors (2):** `DeliveryProcessingException(String message)`; `DeliveryProcessingException(String message, Throwable cause)`
- **Associations:** None

### `DuplicateIsbnException`
- **File:** `exceptions/DuplicateIsbnException.java`
- **Fields (1):** `static final long serialVersionUID = 1L`
- **Public methods and constructors (2):** `DuplicateIsbnException(String message)`; `DuplicateIsbnException(String message, Throwable cause)`
- **Associations:** None

### `InsufficientStockException`
- **File:** `exceptions/InsufficientStockException.java`
- **Fields (1):** `static final long serialVersionUID = 1L`
- **Public methods and constructors (2):** `InsufficientStockException(String message)`; `InsufficientStockException(String message, Throwable cause)`
- **Associations:** None

### `InvalidBookDataException`
- **File:** `exceptions/InvalidBookDataException.java`
- **Fields (1):** `static final long serialVersionUID = 1L`
- **Public methods and constructors (2):** `InvalidBookDataException(String message)`; `InvalidBookDataException(String message, Throwable cause)`
- **Associations:** None

### `InvalidOrderStateException`
- **File:** `exceptions/InvalidOrderStateException.java`
- **Fields (1):** `static final long serialVersionUID = 1L`
- **Public methods and constructors (2):** `InvalidOrderStateException(String message)`; `InvalidOrderStateException(String message, Throwable cause)`
- **Associations:** None

### `InventoryAuditException`
- **File:** `exceptions/InventoryAuditException.java`
- **Fields (1):** `static final long serialVersionUID = 1L`
- **Public methods and constructors (2):** `InventoryAuditException(String message)`; `InventoryAuditException(String message, Throwable cause)`
- **Associations:** None

### `PaymentProcessingException`
- **File:** `exceptions/PaymentProcessingException.java`
- **Fields (1):** `static final long serialVersionUID = 1L`
- **Public methods and constructors (2):** `PaymentProcessingException(String message)`; `PaymentProcessingException(String message, Throwable cause)`
- **Associations:** None

### `SupplierNotFoundException`
- **File:** `exceptions/SupplierNotFoundException.java`
- **Fields (1):** `static final long serialVersionUID = 1L`
- **Public methods and constructors (2):** `SupplierNotFoundException(String message)`; `SupplierNotFoundException(String message, Throwable cause)`
- **Associations:** None

### `AttendanceRecord`
- **File:** `models/AttendanceRecord.java`
- **Fields (7):** `String recordId`, `Employee employee`, `EmployeeShift shift`, `String clockIn`, `String clockOut`, `int breakMinutes`, `String attendanceStatus`
- **Public methods and constructors (7):** `AttendanceRecord(String id,Employee employee,EmployeeShift shift)`; `boolean clockIn(String time)`; `boolean clockOut(String time)`; `boolean recordBreak(int minutes)`; `int calculateWorkedMinutes()`; `boolean isLate(String scheduledStart)`; `Employee getEmployee()`
- **Associations:** `Employee`, `EmployeeShift`

### `Author`
- **File:** `models/Author.java`
- **Fields (5):** `String name`, `String nationality`, `int birthYear`, `String biography`, `ArrayList<String> publishedBooks`
- **Public methods and constructors (6):** `Author(String name, String nationality, int birthYear, String biography)`; `void addBook(String bookTitle)`; `void removeBook(String bookTitle)`; `int countBooks()`; `void displayBooks()`; `void displayAuthor()`
- **Associations:** None

### `Book`
- **File:** `models/Book.java`
- **Fields (11):** `String title`, `String isbn`, `String language`, `int publicationYear`, `int pages`, `double price`, `int quantity`, `Author author`, `Publisher publisher`, `Category category`, `BookFormat format`
- **Public methods and constructors (15):** `Book(String title, String isbn, String language, int publicationYear, int pages, double price, int quantity)`; `void assignAuthor(Author author)`; `void assignPublisher(Publisher publisher)`; `void assignCategory(Category category)`; `void assignFormat(BookFormat format)`; `void displayBook()`; `void updatePrice(double newPrice)`; `void addStock(int amount)`; `void removeStock(int amount)`; `boolean isAvailable()`; `double calculateTotalValue()`; `String getTitle()`; `String getIsbn()`; `int getQuantity()`; `double getPrice()`
- **Associations:** `Author`, `BookFormat`, `Category`, `Publisher`

### `BookConditionRecord`
- **File:** `models/BookConditionRecord.java`
- **Fields (7):** `String inspectionId`, `Book book`, `Employee inspector`, `String inspectionDate`, `String condition`, `String notes`, `boolean approvedForSale`
- **Public methods and constructors (6):** `BookConditionRecord(String id,Book book,Employee inspector,String date,String condition,String notes)`; `boolean classify(String value)`; `boolean approveSale()`; `void addNotes(String value)`; `boolean requiresRemoval()`; `String getCondition()`
- **Associations:** `Book`, `Employee`

### `BookFormat`
- **File:** `models/BookFormat.java`
- **Fields (5):** `String formatName`, `double weight`, `double height`, `double width`, `boolean digital`
- **Public methods and constructors (7):** `BookFormat(String formatName, double weight, double height, double width, boolean digital)`; `double calculateVolume()`; `boolean isDigital()`; `double calculateShippingWeight()`; `void updateDimensions(double height, double width)`; `void displayFormat()`; `String getFormatName()`
- **Associations:** None

### `BookReservation`
- **File:** `models/BookReservation.java`
- **Fields (7):** `String reservationId`, `Book book`, `Customer customer`, `int quantity`, `String reservationDate`, `String expiryDate`, `String status`
- **Public methods and constructors (9):** `BookReservation(String reservationId, Book book, Customer customer, int quantity, String reservationDate, String expiryDate)`; `void cancelReservation()`; `void completeReservation()`; `void extendReservation(String newExpiryDate)`; `boolean isActive()`; `boolean isValidQuantity()`; `void displayReservation()`; `String getReservationId()`; `String getStatus()`
- **Associations:** `Book`, `Customer`

### `BookReview`
- **File:** `models/BookReview.java`
- **Fields (7):** `String reviewId`, `Book book`, `Customer customer`, `int rating`, `String comment`, `String reviewDate`, `int helpfulVotes`
- **Public methods and constructors (10):** `BookReview(String reviewId, Book book, Customer customer, int rating, String comment, String reviewDate)`; `void updateComment(String newComment)`; `void updateRating(int newRating)`; `void markHelpful()`; `boolean isPositiveReview()`; `boolean isValidRating()`; `void displayReview()`; `String getReviewId()`; `int getRating()`; `int getHelpfulVotes()`
- **Associations:** `Book`, `Customer`

### `Category`
- **File:** `models/Category.java`
- **Fields (4):** `String categoryName`, `String description`, `int categoryId`, `ArrayList<String> books`
- **Public methods and constructors (7):** `Category(int categoryId, String categoryName, String description)`; `void addBook(String title)`; `void removeBook(String title)`; `boolean containsBook(String title)`; `int getBookCount()`; `void displayCategory()`; `String getCategoryName()`
- **Associations:** None

### `Coupon`
- **File:** `models/Coupon.java`
- **Fields (7):** `String code`, `double discountAmount`, `double minimumPurchase`, `int usageLimit`, `int usageCount`, `String expiryDate`, `boolean enabled`
- **Public methods and constructors (7):** `Coupon(String code, double discountAmount, double minimumPurchase, int usageLimit, String expiryDate)`; `boolean isValid(String date, double basketTotal)`; `double apply(double basketTotal, String date)`; `void disable()`; `boolean extendExpiry(String date)`; `int remainingUses()`; `String getCode()`
- **Associations:** None

### `Customer`
- **File:** `models/Customer.java`
- **Fields (6):** `String customerId`, `String fullName`, `String email`, `String phone`, `String address`, `int loyaltyPoints`
- **Public methods and constructors (8):** `Customer(String customerId, String fullName, String email, String phone, String address)`; `void updateContactInfo(String email, String phone)`; `void updateAddress(String newAddress)`; `void earnLoyaltyPoints(int points)`; `boolean hasValidEmail()`; `void displayCustomer()`; `String getCustomerId()`; `String getFullName()`
- **Associations:** None

### `Delivery`
- **File:** `models/Delivery.java`
- **Fields (8):** `String deliveryId`, `Supplier supplier`, `Employee employee`, `PurchaseOrder purchaseOrder`, `String deliveryDate`, `String deliveryStatus`, `String trackingNumber`, `String notes`
- **Public methods and constructors (11):** `Delivery(String deliveryId, Supplier supplier, Employee employee, PurchaseOrder purchaseOrder, String deliveryDate, String trackingNumber)`; `boolean receiveDelivery()`; `boolean rejectDelivery(String reason)`; `boolean isDeliveryComplete()`; `void updateTrackingNumber(String newNumber)`; `void updateDeliveryDate(String newDate)`; `void addDeliveryNotes(String newNotes)`; `boolean hasValidDetails()`; `void displayDelivery()`; `String getDeliveryId()`; `String getDeliveryStatus()`
- **Associations:** `Employee`, `PurchaseOrder`, `Supplier`

### `Discount`
- **File:** `models/Discount.java`
- **Fields (7):** `String discountId`, `String name`, `double percentage`, `String startDate`, `String endDate`, `boolean active`, `String categoryName`
- **Public methods and constructors (8):** `Discount(String id, String name, double percentage, String startDate, String endDate, String categoryName)`; `boolean activate()`; `void deactivate()`; `boolean isApplicable(String date, Category category)`; `double calculateDiscount(double price)`; `double applyToPrice(double price)`; `boolean changePercentage(double value)`; `String getDiscountId()`
- **Associations:** `Category`

### `Employee`
- **File:** `models/Employee.java`
- **Fields (9):** `String employeeId`, `String firstName`, `String lastName`, `String email`, `String phone`, `String position`, `double salary`, `String hireDate`, `int yearsOfExperience`
- **Public methods and constructors (12):** `Employee(String employeeId, String firstName, String lastName, String email, String phone, String position, double salary, String hireDate, int yearsOfExperience)`; `String getFullName()`; `void updatePosition(String newPosition)`; `void updateSalary(double newSalary)`; `void increaseSalary(double percentage)`; `boolean hasValidEmail()`; `boolean isExperienced()`; `double calculateAnnualSalary()`; `void displayEmployee()`; `String getEmployeeId()`; `double getSalary()`; `String getPosition()`
- **Associations:** None

### `EmployeeShift`
- **File:** `models/EmployeeShift.java`
- **Fields (7):** `String shiftId`, `Employee employee`, `String shiftDate`, `String startTime`, `String endTime`, `String role`, `String status`
- **Public methods and constructors (7):** `EmployeeShift(String id,Employee employee,String date,String start,String end,String role)`; `boolean validateTimes()`; `boolean startShift(String time)`; `boolean finishShift()`; `boolean cancelShift()`; `boolean isAssignedTo(Employee candidate)`; `String getStatus()`
- **Associations:** `Employee`

### `InventoryAudit`
- **File:** `models/InventoryAudit.java`
- **Fields (8):** `String auditId`, `Warehouse warehouse`, `Employee auditor`, `String auditDate`, `final List<Book> auditedBooks = new ArrayList<>()`, `final List<Integer> countedQuantities = new ArrayList<>()`, `String auditStatus`, `int totalDiscrepancies`
- **Public methods and constructors (13):** `InventoryAudit(String auditId, Warehouse warehouse, Employee auditor, String auditDate)`; `boolean recordPhysicalCount(Book book, int actualQuantity)`; `int calculateBookDiscrepancy(Book book)`; `void calculateTotalDiscrepancies()`; `boolean applyStockCorrections()`; `boolean reopen()`; `boolean validateAudit()`; `void completeWithoutCorrections()`; `int countAuditedBooks()`; `boolean isAuditComplete()`; `void displayAudit()`; `String getAuditStatus()`; `int getTotalDiscrepancies()`
- **Associations:** `Book`, `Employee`, `Warehouse`

### `InventoryReport`
- **File:** `models/InventoryReport.java`
- **Fields (5):** `String reportId`, `Warehouse warehouse`, `String generatedDate`, `String reportType`, `String notes`
- **Public methods and constructors (11):** `InventoryReport(String reportId, Warehouse warehouse, String generatedDate, String reportType)`; `void generateSummary()`; `double calculateAverageStock()`; `double calculateAverageBookValue()`; `boolean isBookLowStock(Book book, int minimumStock)`; `int countLowStockBooks(ArrayList<Book> books, int minimumStock)`; `String assessInventoryValue()`; `void updateNotes(String newNotes)`; `String createReportTitle()`; `void displayReportDetails()`; `String getReportId()`
- **Associations:** `Book`, `Warehouse`

### `InventoryTransfer`
- **File:** `models/InventoryTransfer.java`
- **Fields (7):** `String transferId`, `Book book`, `WarehouseSection source`, `WarehouseSection destination`, `int quantity`, `String requestedDate`, `String status`
- **Public methods and constructors (7):** `InventoryTransfer(String id,Book book,WarehouseSection source,WarehouseSection destination,int quantity,String date)`; `boolean validate()`; `boolean approve()`; `boolean execute()`; `boolean cancel()`; `boolean isCompleted()`; `String getStatus()`
- **Associations:** `Book`, `WarehouseSection`

### `Invoice`
- **File:** `models/Invoice.java`
- **Fields (9):** `String invoiceId`, `Order order`, `Customer customer`, `String issueDate`, `String dueDate`, `String status`, `double taxRate`, `double discountAmount`, `final List<InvoiceLine> lines = new ArrayList<>()`
- **Public methods and constructors (13):** `Invoice(String invoiceId, Order order, Customer customer, String issueDate, String dueDate, double taxRate)`; `boolean addLine(InvoiceLine line)`; `double calculateSubtotal()`; `double calculateTax()`; `double calculateTotal()`; `boolean applyDiscount(double amount)`; `boolean issue()`; `boolean markPaid()`; `boolean cancel()`; `boolean isOverdue(String today)`; `void displayInvoice()`; `String getInvoiceId()`; `String getStatus()`
- **Associations:** `Customer`, `InvoiceLine`, `Order`

### `InvoiceLine`
- **File:** `models/InvoiceLine.java`
- **Fields (6):** `String lineId`, `Book book`, `int quantity`, `double unitPrice`, `double taxRate`, `String description`
- **Public methods and constructors (9):** `InvoiceLine(String lineId, Book book, int quantity, double unitPrice, double taxRate, String description)`; `boolean updateQuantity(int value)`; `boolean updateUnitPrice(double value)`; `double calculateNet()`; `double calculateTax()`; `double calculateLineTotal()`; `boolean refersTo(Book candidate)`; `String describe()`; `Book getBook()`
- **Associations:** `Book`

### `LoyaltyAccount`
- **File:** `models/LoyaltyAccount.java`
- **Fields (6):** `String accountId`, `Customer customer`, `int pointsBalance`, `int lifetimePoints`, `String tier`, `String lastActivityDate`
- **Public methods and constructors (8):** `LoyaltyAccount(String accountId, Customer customer, String date)`; `boolean earnPoints(double purchaseAmount)`; `boolean redeemPoints(int points)`; `double calculateRewardValue(int pointsPerCurrencyUnit)`; `void expirePoints(int points)`; `void recordActivity(String date)`; `String getTier()`; `int getPointsBalance()`
- **Associations:** `Customer`

### `Order`
- **File:** `models/Order.java`
- **Fields (6):** `String orderId`, `Customer customer`, `ArrayList<OrderItem> items`, `double totalAmount`, `String orderStatus`, `String orderDate`
- **Public methods and constructors (9):** `Order(String orderId, Customer customer, String orderDate)`; `void addBook(Book book, int quantity)`; `void removeItem(OrderItem item)`; `void updateOrderStatus(String status)`; `double calculateOrderTotal()`; `boolean isOrderEmpty()`; `void displayOrder()`; `List<OrderItem> getItems()`; `String getOrderId()`
- **Associations:** `Book`, `Customer`, `OrderItem`

### `OrderItem`
- **File:** `models/OrderItem.java`
- **Fields (3):** `Book book`, `int quantity`, `double unitPrice`
- **Public methods and constructors (6):** `OrderItem(Book book, int quantity)`; `double calculateSubtotal()`; `Book getBook()`; `int getQuantity()`; `double getUnitPrice()`; `void displayItem()`
- **Associations:** `Book`

### `Payment`
- **File:** `models/Payment.java`
- **Fields (9):** `String paymentId`, `Order order`, `Customer customer`, `double amount`, `String paymentMethod`, `String paymentDate`, `String paymentStatus`, `String transactionReference`, `double refundedAmount`
- **Public methods and constructors (12):** `Payment(String paymentId, Order order, Customer customer, double amount, String paymentMethod, String paymentDate)`; `void processPayment()`; `void refundPayment()`; `boolean refundAmount(double refundValue)`; `double getRemainingAmount()`; `double getRefundedAmount()`; `void changePaymentMethod(String newMethod)`; `boolean isPaymentSuccessful()`; `boolean isValidAmount()`; `void displayPayment()`; `String getPaymentStatus()`; `double getAmount()`
- **Associations:** `Customer`, `Order`

### `ProcurementPlan`
- **File:** `models/ProcurementPlan.java`
- **Fields (7):** `String planId`, `Supplier supplier`, `Employee preparedBy`, `String planningPeriod`, `double budget`, `String status`, `final List<RestockRequest> requests=new ArrayList<>()`
- **Public methods and constructors (8):** `ProcurementPlan(String id,Supplier supplier,Employee preparedBy,String period,double budget)`; `boolean addRequest(RestockRequest request)`; `double calculateEstimatedSpend()`; `boolean isWithinBudget()`; `boolean approvePlan()`; `boolean cancelPlan()`; `int countRequests()`; `String getStatus()`
- **Associations:** `Employee`, `RestockRequest`, `Supplier`

### `Publisher`
- **File:** `models/Publisher.java`
- **Fields (5):** `String name`, `String address`, `String email`, `String phone`, `String country`
- **Public methods and constructors (7):** `Publisher(String name, String address, String email, String phone, String country)`; `void updateEmail(String newEmail)`; `void updateAddress(String newAddress)`; `void updatePhone(String newPhone)`; `boolean hasValidEmail()`; `void displayPublisher()`; `String getName()`
- **Associations:** None

### `PurchaseOrder`
- **File:** `models/PurchaseOrder.java`
- **Fields (8):** `String purchaseOrderId`, `Supplier supplier`, `ArrayList<Book> books`, `ArrayList<Integer> quantities`, `double totalCost`, `String orderDate`, `String expectedDeliveryDate`, `String status`
- **Public methods and constructors (13):** `PurchaseOrder(String purchaseOrderId, Supplier supplier, String orderDate, String expectedDeliveryDate)`; `boolean addBook(Book book, int quantity)`; `boolean removeBook(Book book)`; `double calculateTotalCost()`; `int calculateTotalQuantity()`; `boolean confirmOrder()`; `boolean cancelOrder()`; `boolean markAsDelivered()`; `boolean isOrderEmpty()`; `void updateExpectedDeliveryDate(String newDate)`; `void displayPurchaseOrder()`; `String getPurchaseOrderId()`; `String getStatus()`
- **Associations:** `Book`, `Supplier`

### `Refund`
- **File:** `models/Refund.java`
- **Fields (7):** `String refundId`, `Payment payment`, `ReturnRequest returnRequest`, `double amount`, `String refundDate`, `String method`, `String status`
- **Public methods and constructors (7):** `Refund(String refundId, Payment payment, ReturnRequest request, double amount, String refundDate, String method)`; `boolean validate()`; `boolean process()`; `boolean cancel()`; `double calculateRemainingPayment()`; `boolean usesOriginalMethod()`; `String getStatus()`
- **Associations:** `Payment`, `ReturnRequest`

### `RestockRequest`
- **File:** `models/RestockRequest.java`
- **Fields (9):** `String requestId`, `Book book`, `Supplier supplier`, `Employee requestedBy`, `int requestedQuantity`, `String requestDate`, `String priority`, `String status`, `String reason`
- **Public methods and constructors (12):** `RestockRequest(String requestId, Book book, Supplier supplier, Employee requestedBy, int requestedQuantity, String requestDate, String priority, String reason)`; `boolean isValidRequest()`; `boolean approveRequest()`; `boolean rejectRequest(String rejectionReason)`; `boolean cancelRequest()`; `double calculateEstimatedCost()`; `boolean isUrgent()`; `void updateRequestedQuantity(int newQuantity)`; `PurchaseOrder createPurchaseOrder( String purchaseOrderId, String orderDate, String expectedDeliveryDate)`; `void displayRequest()`; `String getRequestId()`; `String getStatus()`
- **Associations:** `Book`, `Employee`, `Supplier`

### `ReturnRequest`
- **File:** `models/ReturnRequest.java`
- **Fields (8):** `String returnId`, `Order order`, `Customer customer`, `Book book`, `int quantity`, `String requestDate`, `String reason`, `String status`
- **Public methods and constructors (8):** `ReturnRequest(String returnId, Order order, Customer customer, Book book, int quantity, String requestDate, String reason)`; `boolean validate()`; `boolean approve()`; `boolean reject(String note)`; `boolean receiveReturnedBook(boolean resellable)`; `double calculateRefundValue()`; `boolean isResellableReason()`; `String getStatus()`
- **Associations:** `Book`, `Customer`, `Order`

### `Shelf`
- **File:** `models/Shelf.java`
- **Fields (7):** `String shelfId`, `WarehouseZone zone`, `String aisleCode`, `int capacity`, `int weightLimit`, `boolean accessible`, `final List<Book> books=new ArrayList<>()`
- **Public methods and constructors (7):** `Shelf(String id,WarehouseZone zone,String aisleCode,int capacity,int weightLimit,boolean accessible)`; `boolean storeBook(Book book)`; `boolean removeBook(Book book)`; `boolean containsIsbn(String isbn)`; `int availableSlots()`; `boolean canEmployeeAccess(Employee employee)`; `int bookCount()`
- **Associations:** `Book`, `Employee`, `WarehouseZone`

### `Shipment`
- **File:** `models/Shipment.java`
- **Fields (9):** `String shipmentId`, `Order order`, `Delivery delivery`, `String carrier`, `String trackingNumber`, `String dispatchDate`, `String deliveryDate`, `String status`, `final List<ShipmentItem> items = new ArrayList<>()`
- **Public methods and constructors (9):** `Shipment(String shipmentId, Order order, Delivery delivery, String carrier, String trackingNumber, String dispatchDate)`; `boolean addItem(ShipmentItem item)`; `boolean dispatch()`; `boolean markDelivered(String date)`; `boolean reportIssue(String details)`; `int countItems()`; `int countUnits()`; `boolean hasTracking()`; `String getStatus()`
- **Associations:** `Delivery`, `Order`, `ShipmentItem`

### `ShipmentItem`
- **File:** `models/ShipmentItem.java`
- **Fields (6):** `String itemId`, `Book book`, `int quantity`, `double declaredValue`, `String packagingType`, `boolean inspected`
- **Public methods and constructors (7):** `ShipmentItem(String itemId, Book book, int quantity, double declaredValue, String packagingType)`; `boolean inspect()`; `boolean changeQuantity(int value)`; `double calculateDeclaredTotal()`; `boolean requiresSpecialPackaging()`; `int getQuantity()`; `Book getBook()`
- **Associations:** `Book`

### `StockAlert`
- **File:** `models/StockAlert.java`
- **Fields (8):** `String alertId`, `Book book`, `int minimumStockLevel`, `int currentStock`, `String alertDate`, `String alertType`, `String alertStatus`, `String message`
- **Public methods and constructors (11):** `StockAlert(String alertId, Book book, int minimumStockLevel, String alertDate)`; `boolean checkStockLevel()`; `void updateMinimumStockLevel(int newLevel)`; `boolean resolveAlert()`; `boolean isAlertActive()`; `int calculateStockDeficit()`; `String determineSeverity()`; `void updateAlertMessage(String newMessage)`; `void displayAlert()`; `String getAlertId()`; `String getAlertStatus()`
- **Associations:** `Book`

### `StockMovement`
- **File:** `models/StockMovement.java`
- **Fields (8):** `String movementId`, `Book book`, `Employee employee`, `String movementType`, `int quantity`, `String movementDate`, `String reason`, `boolean applied`
- **Public methods and constructors (7):** `StockMovement(String movementId, Book book, Employee employee, String movementType, int quantity, String movementDate, String reason)`; `boolean applyMovement()`; `boolean reverseMovement()`; `boolean isValidMovement()`; `void displayMovement()`; `String getMovementId()`; `boolean isApplied()`
- **Associations:** `Book`, `Employee`

### `Supplier`
- **File:** `models/Supplier.java`
- **Fields (8):** `String supplierId`, `String companyName`, `String contactPerson`, `String email`, `String phone`, `String address`, `String country`, `ArrayList<Book> suppliedBooks`
- **Public methods and constructors (12):** `Supplier(String supplierId, String companyName, String contactPerson, String email, String phone, String address, String country)`; `void addSuppliedBook(Book book)`; `void removeSuppliedBook(Book book)`; `int countSuppliedBooks()`; `void updateEmail(String newEmail)`; `void updatePhone(String newPhone)`; `void updateAddress(String newAddress)`; `boolean hasValidEmail()`; `double calculateSuppliedBooksValue()`; `void displaySupplier()`; `String getSupplierId()`; `String getCompanyName()`
- **Associations:** `Book`

### `SupplierContract`
- **File:** `models/SupplierContract.java`
- **Fields (8):** `String contractId`, `Supplier supplier`, `String startDate`, `String endDate`, `double discountPercentage`, `double spendingLimit`, `double currentSpending`, `String status`
- **Public methods and constructors (8):** `SupplierContract(String id,Supplier supplier,String startDate,String endDate,double discount,double limit)`; `boolean isValidOn(String date)`; `boolean recordPurchase(double amount,String date)`; `double applySupplierDiscount(double amount)`; `void suspend()`; `boolean renew(String newEndDate)`; `double remainingLimit()`; `Supplier getSupplier()`
- **Associations:** `Supplier`

### `Warehouse`
- **File:** `models/Warehouse.java`
- **Fields (3):** `String warehouseName`, `String location`, `ArrayList<Book> books`
- **Public methods and constructors (9):** `Warehouse(String warehouseName, String location)`; `void addBook(Book book)`; `void removeBook(String isbn)`; `Book findBookByISBN(String isbn)`; `Book findBookByTitle(String title)`; `void displayAllBooks()`; `double calculateInventoryValue()`; `int countTotalCopies()`; `int countBookTitles()`
- **Associations:** `Book`

### `WarehouseSection`
- **File:** `models/WarehouseSection.java`
- **Fields (5):** `String sectionId`, `String sectionName`, `String locationCode`, `int maximumCapacity`, `ArrayList<Book> books`
- **Public methods and constructors (11):** `WarehouseSection(String sectionId, String sectionName, String locationCode, int maximumCapacity)`; `void addBook(Book book)`; `void removeBook(Book book)`; `int countBooks()`; `boolean isFull()`; `int getAvailableCapacity()`; `boolean containsBook(Book book)`; `void renameSection(String newName)`; `void displaySection()`; `String getSectionName()`; `String getSectionId()`
- **Associations:** `Book`

### `WarehouseZone`
- **File:** `models/WarehouseZone.java`
- **Fields (7):** `String zoneId`, `String zoneName`, `String temperatureRange`, `int capacity`, `boolean restricted`, `String purpose`, `final List<WarehouseSection> sections=new ArrayList<>()`
- **Public methods and constructors (8):** `WarehouseZone(String id,String name,String temperatureRange,int capacity,boolean restricted,String purpose)`; `boolean addSection(WarehouseSection section)`; `boolean removeSection(WarehouseSection section)`; `boolean isFull()`; `int availableCapacity()`; `boolean canStoreTemperatureSensitiveGoods()`; `void changePurpose(String value)`; `int sectionCount()`
- **Associations:** `WarehouseSection`

### `WarehouseService`
- **File:** `services/WarehouseService.java`
- **Fields (4):** `final Warehouse warehouse`, `final Map<String, Customer> customers = new LinkedHashMap<>()`, `final Map<String, Supplier> suppliers = new LinkedHashMap<>()`, `final Map<String, Order> orders = new LinkedHashMap<>()`
- **Public methods and constructors (16):** `WarehouseService(Warehouse warehouse)`; `void registerBook(Book book)`; `Book findBook(String isbn)`; `void registerCustomer(Customer customer)`; `void registerSupplier(Supplier supplier)`; `Order placeOrder(String orderId, String customerId, String isbn, int quantity, String date)`; `void removeBook(String isbn)`; `void receiveStock(String isbn, int quantity)`; `void processPayment(Payment payment)`; `void receiveDelivery(Delivery delivery)`; `void addBookToSection(WarehouseSection section, Book book)`; `Supplier requireSupplier(String supplierId)`; `double inventoryValue()`; `int totalCopies()`; `int titleCount()`; `Warehouse getWarehouse()`
- **Associations:** `Book`, `Customer`, `Delivery`, `Order`, `Payment`, `Supplier`, `Warehouse`, `WarehouseSection`

## Custom exceptions

All custom exceptions extend `BookWarehouseException`, which extends `RuntimeException`. `WarehouseService` uses the domain exceptions to report invalid operations.

- **`BookNotFoundException`** — domain error type with message and message-plus-cause constructors.
- **`BookWarehouseException`** — domain error type with message and message-plus-cause constructors.
- **`CapacityExceededException`** — domain error type with message and message-plus-cause constructors.
- **`CustomerNotFoundException`** — domain error type with message and message-plus-cause constructors.
- **`DeliveryProcessingException`** — domain error type with message and message-plus-cause constructors.
- **`DuplicateIsbnException`** — domain error type with message and message-plus-cause constructors.
- **`InsufficientStockException`** — domain error type with message and message-plus-cause constructors.
- **`InvalidBookDataException`** — domain error type with message and message-plus-cause constructors.
- **`InvalidOrderStateException`** — domain error type with message and message-plus-cause constructors.
- **`InventoryAuditException`** — domain error type with message and message-plus-cause constructors.
- **`PaymentProcessingException`** — domain error type with message and message-plus-cause constructors.
- **`SupplierNotFoundException`** — domain error type with message and message-plus-cause constructors.

## Testing

JUnit 5 tests are in `src/test/java/ProjectTest.java`. Run `./test.sh` or `mvn clean verify`. JaCoCo writes an HTML report to `target/site/jacoco/index.html`; the Maven verification is configured to fail if line coverage is below 90%.

## GitHub automation

- `.github/workflows/ci.yml` runs linting, tests and coverage checks on pushes and pull requests, and uploads the coverage report.
- `.github/workflows/release.yml` verifies and publishes the runnable JAR to GitHub Releases when a version tag such as `v1.0.0` is pushed.
- `.gitignore` excludes compiled classes, build output, IDE files and OS metadata.

## Repository workflow

Keep all four course labs in one repository under `lab1/`, `lab2/`, `lab3/` and `lab4/`. Work on feature branches, open pull requests, and merge to `main` only after instructor approval. Do not commit `target/`, `bin/`, `.class` files or IDE metadata.

## Defence preparation

Be ready to explain object associations, encapsulation, inventory invariants, order and delivery state transitions, stock correction during an audit, exception propagation through `WarehouseService`, and how the CLI calls the service without embedding domain rules.
