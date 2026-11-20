import app.WarehouseCli;
import exceptions.*;
import models.*;
import org.junit.jupiter.api.Test;
import services.WarehouseService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {
    private Book book(String title, String isbn, int stock) {
        return new Book(title, isbn, "English", 2020, 200, 20.0, stock);
    }

    private Employee employee() {
        return new Employee("E1", "Ada", "Moyo", "ada@example.com", "+375291234567", "Manager", 1200, "2022-01-01", 5);
    }

    private Customer customer() {
        return new Customer("C1", "Sam Customer", "sam@example.com", "+375291234567", "Minsk");
    }

    private Supplier supplier() {
        return new Supplier("S1", "Book Supply Ltd", "Pat Supplier", "supply@example.com", "+263771234567", "Harare", "Zimbabwe");
    }

    private Warehouse warehouse(Book book) {
        Warehouse warehouse = new Warehouse("Central", "Minsk");
        warehouse.addBook(book);
        return warehouse;
    }

    @Test
    void coreCatalogAndPeopleModelsWork() {
        Author author = new Author("Writer", "Zimbabwean", 1970, "Biography");
        author.addBook("Novel");
            author.addBook("Poems");
        assertEquals(2, author.countBooks());
        author.removeBook("Poems");
            assertEquals(1, author.countBooks());
        author.displayBooks();
            author.displayAuthor();

        Publisher publisher = new Publisher("Press", "Harare", "press@example.com", "12345", "Zimbabwe");
        assertTrue(publisher.hasValidEmail());
        publisher.updateEmail("bad-email");
            assertFalse(publisher.hasValidEmail());
        publisher.updateEmail("new@example.com");
            publisher.updateAddress("Minsk");
            publisher.updatePhone("999");
            publisher.displayPublisher();

        Category category = new Category(1, "Fiction", "Stories");
        assertEquals("Fiction", category.getCategoryName());
        category.addBook("Novel");
            assertTrue(category.containsBook("Novel"));
        category.displayCategory();
            category.removeBook("Novel");
            assertEquals(0, category.getBookCount());

        BookFormat print = new BookFormat("Paperback", 0.4, 20, 13, false);
        assertEquals(520.0, print.calculateVolume(), 0.0001);
            assertFalse(print.isDigital());
        assertEquals(0.4, print.calculateShippingWeight(), 0.0001);
            print.updateDimensions(21, 14);
            print.displayFormat();
        BookFormat digital = new BookFormat("E-book", 0, 1, 1, true);
        assertTrue(digital.isDigital());
            assertEquals(0.0, digital.calculateShippingWeight(), 0.0001);

        Book book = book("Novel", "ISBN1", 10);
        assertEquals("Novel", book.getTitle());
            assertEquals("ISBN1", book.getIsbn());
        book.assignAuthor(author);
            book.assignPublisher(publisher);
            book.assignCategory(category);
            book.assignFormat(print);
        book.displayBook();
            assertTrue(book.isAvailable());
            assertEquals(200.0, book.calculateTotalValue(), 0.0001);
        book.updatePrice(25);
            book.updatePrice(-1);
            book.addStock(2);
            book.addStock(-1);
            book.removeStock(3);
            book.removeStock(100);
        assertEquals(9, book.getQuantity());
            assertEquals(25.0, book.getPrice(), 0.0001);

        Customer customer = customer();
        assertTrue(customer.hasValidEmail());
            customer.earnLoyaltyPoints(25);
            customer.updateContactInfo("new@example.com", "123");
            customer.updateAddress("Warsaw");
            customer.displayCustomer();
        Employee employee = employee();
            assertTrue(employee.hasValidEmail());
            assertTrue(employee.isExperienced());
        employee.updatePosition("Supervisor");
            employee.updateSalary(1300);
            employee.increaseSalary(10);
            assertEquals(1430.0, employee.getSalary(), 0.0001);
        assertEquals(17160.0, employee.calculateAnnualSalary(), 0.0001);
            employee.displayEmployee();

        Supplier supplier = supplier();
            supplier.addSuppliedBook(book);
            assertEquals(1, supplier.countSuppliedBooks());
        assertTrue(supplier.hasValidEmail());
            supplier.updateEmail("bad@");
            assertFalse(supplier.hasValidEmail());
        supplier.updateEmail("ok@example.com");
            supplier.updatePhone("456");
            supplier.updateAddress("Minsk");
        assertTrue(supplier.calculateSuppliedBooksValue()>0);
            supplier.displaySupplier();
            supplier.removeSuppliedBook(book);

        Warehouse warehouse = warehouse(book);
            assertEquals(1, warehouse.countBookTitles());
        assertEquals(9, warehouse.countTotalCopies());
            assertNotNull(warehouse.findBookByTitle("Novel"));
        assertNotNull(warehouse.findBookByISBN("ISBN1"));
            assertTrue(warehouse.calculateInventoryValue()>0);
        warehouse.displayAllBooks();
            warehouse.removeBook("ISBN1");
            assertEquals(0, warehouse.countBookTitles());
    }

    @Test
    void orderPaymentReviewReservationAndWarehouseOperationsWork() {
        Book book = book("Novel", "ISBN2", 20);
        Customer customer = customer();
        Order order = new Order("O1", customer, "2026-01-01");
        assertTrue(order.isOrderEmpty());
            order.addBook(book, 2);
            assertFalse(order.isOrderEmpty());
        assertEquals(18, book.getQuantity());
            assertEquals(40.0, order.calculateOrderTotal(), 0.0001);
        order.updateOrderStatus("Confirmed");
            order.displayOrder();
        OrderItem item = new OrderItem(book, 1);
            assertEquals(20.0, item.calculateSubtotal(), 0.0001);
            item.displayItem();
        assertEquals(book, item.getBook());
            assertEquals(1, item.getQuantity());
            assertEquals(20.0, item.getUnitPrice(), 0.0001);
        order.removeItem(order.getItems().get(0));
            assertTrue(order.isOrderEmpty());
            assertEquals(20, book.getQuantity());

        Payment payment = new Payment("P1", order, customer, 40, "Card", "2026-01-01");
        assertTrue(payment.isValidAmount());
            payment.changePaymentMethod("Cash");
            payment.processPayment();
        assertTrue(payment.isPaymentSuccessful());
            payment.displayPayment();
            payment.refundPayment();
            assertEquals("Refunded", payment.getPaymentStatus());
            assertEquals(40.0, payment.getRefundedAmount(), 0.0001);

        BookReview review = new BookReview("R1", book, customer, 5, "Great read", "2026-01-01");
        assertTrue(review.isValidRating());
            assertTrue(review.isPositiveReview());
            review.updateComment("Excellent");
            review.updateRating(4);
            review.markHelpful();
            review.displayReview();
        BookReservation reservation = new BookReservation("BR1", book, customer, 2, "2026-01-01", "2026-01-10");
        assertTrue(reservation.isActive());
            assertTrue(reservation.isValidQuantity());
            reservation.extendReservation("2026-01-15");
            reservation.displayReservation();
            reservation.completeReservation();
            assertFalse(reservation.isActive());

        WarehouseSection section = new WarehouseSection("SEC", "Fiction", "A1", 2);
        section.addBook(book);
            assertTrue(section.containsBook(book));
            assertFalse(section.isFull());
            assertEquals(1, section.getAvailableCapacity());
        section.addBook(book("Other", "ISBN3", 1));
            assertTrue(section.isFull());
            section.removeBook(book);
            section.renameSection("New Fiction");
            section.displaySection();
        StockMovement movement = new StockMovement("M1", book, employee(), "IN", 3, "2026-01-02", "Delivery");
        assertTrue(movement.isValidMovement());
            assertTrue(movement.applyMovement());
            assertTrue(movement.isApplied());
        assertTrue(movement.reverseMovement());
            assertFalse(movement.isApplied());
            movement.displayMovement();
    }

    @Test
    void reportingProcurementDeliveryAndAuditModelsWork() {
        Book book = book("Novel", "ISBN4", 20);
            Warehouse warehouse = warehouse(book);
        Employee employee = employee();
            Supplier supplier = supplier();
            Customer customer = customer();
        InventoryReport report = new InventoryReport("REP", warehouse, "2026-01-01", "Monthly");
        report.generateSummary();
            assertEquals(20.0, report.calculateAverageStock(), 0.0001);
        assertEquals(400.0, report.calculateAverageBookValue(), 0.0001);
            assertFalse(report.isBookLowStock(book, 5));
        assertEquals(0, report.countLowStockBooks(new ArrayList<>(java.util.List.of(book)), 5));
        report.updateNotes("Checked");
            report.displayReportDetails();
            assertNotNull(report.createReportTitle());
            assertNotNull(report.assessInventoryValue());

        PurchaseOrder po = new PurchaseOrder("PO", supplier, "2026-01-01", "2026-01-10");
        assertTrue(po.addBook(book, 3));
            assertEquals(60.0, po.calculateTotalCost(), 0.0001);
            assertEquals(3, po.calculateTotalQuantity());
        assertTrue(po.confirmOrder());
            po.updateExpectedDeliveryDate("2026-01-12");
            po.displayPurchaseOrder();
        PurchaseOrder directPo = new PurchaseOrder("DIRECT", supplier, "2026-01-01", "2026-01-10");
            directPo.addBook(book, 1);
            directPo.confirmOrder();
            assertTrue(directPo.markAsDelivered());
        Delivery delivery = new Delivery("D", supplier, employee, po, "2026-01-10", "TRACK");
        assertTrue(delivery.hasValidDetails());
            assertTrue(delivery.receiveDelivery());
            assertTrue(delivery.isDeliveryComplete());
        delivery.addDeliveryNotes("Good");
            delivery.displayDelivery();

        RestockRequest request = new RestockRequest("REQ", book, supplier, employee, 2, "2026-01-01", "High", "Low stock");
        assertTrue(request.isValidRequest());
            assertTrue(request.isUrgent());
            assertTrue(request.calculateEstimatedCost()>0);
            request.updateRequestedQuantity(3);
            request.displayRequest();
            assertTrue(request.approveRequest());
        PurchaseOrder generated = request.createPurchaseOrder("PO2", "2026-01-01", "2026-01-10");
            assertNotNull(generated);

        StockAlert alert = new StockAlert("A", book, 100, "2026-01-01");
        assertTrue(alert.checkStockLevel());
            assertTrue(alert.isAlertActive());
            assertTrue(alert.calculateStockDeficit()>0);
            assertEquals("Open", alert.getAlertStatus());
        book.addStock(100);
            assertTrue(alert.resolveAlert());
            alert.displayAlert();

        InventoryAudit audit = new InventoryAudit("AUD", warehouse, employee, "2026-01-01");
        assertTrue(audit.validateAudit());
            assertTrue(audit.recordPhysicalCount(book, book.getQuantity()+2));
            assertEquals(1, audit.countAuditedBooks());
            audit.displayAudit();
        assertEquals(2, audit.calculateBookDiscrepancy(book));
            audit.calculateTotalDiscrepancies();
        assertTrue(audit.applyStockCorrections());
            assertTrue(audit.isAuditComplete());
    }

    @Test
    void extendedSalesAndShippingModelsWork() {
        Book book = book("Novel", "ISBN5", 15);
            Customer customer = customer();
            Employee employee = employee();
            Supplier supplier = supplier();
        Order order = new Order("O2", customer, "2026-01-01");
            order.addBook(book, 2);
        Invoice invoice = new Invoice("INV", order, customer, "2026-01-01", "2026-01-31", 10);
        InvoiceLine line = new InvoiceLine("L1", book, 2, 20, 10, "Novel copies");
        assertTrue(invoice.addLine(line));
            assertEquals(4.0, invoice.calculateTax(), 0.0001);
            assertEquals(44.0, invoice.calculateTotal(), 0.0001);
            assertTrue(invoice.applyDiscount(4));
        assertTrue(invoice.issue());
            assertTrue(invoice.isOverdue("2026-02-01"));
            assertTrue(invoice.markPaid());
            invoice.displayInvoice();
        assertTrue(line.updateQuantity(3));
            assertTrue(line.updateUnitPrice(18));
            assertTrue(line.refersTo(book));
            assertTrue(line.calculateNet()>0);
            assertTrue(line.calculateTax()>0);
            assertTrue(line.calculateLineTotal()>0);
            assertTrue(line.describe().contains("L1"));

        Delivery delivery = new Delivery("D2", supplier, employee, new PurchaseOrder("PO3", supplier, "2026-01-01", "2026-01-02"),
            "2026-01-02", "TR2");
        Shipment shipment = new Shipment("SH", order, delivery, "Carrier", "TR2", "2026-01-02");
        ShipmentItem shipmentItem = new ShipmentItem("SI", book, 2, 20, "Protective box");
        assertTrue(shipmentItem.inspect());
            assertTrue(shipmentItem.calculateDeclaredTotal()>0);
            assertTrue(shipmentItem.requiresSpecialPackaging());
            assertTrue(shipment.addItem(shipmentItem));
        assertEquals(1, shipment.countItems());
            assertEquals(2, shipment.countUnits());
            assertTrue(shipment.hasTracking());
            assertTrue(shipment.dispatch());
            assertTrue(shipment.markDelivered("2026-01-03"));

        ReturnRequest request = new ReturnRequest("RET", order, customer, book, 1, "2026-01-04", "Wrong title");
        assertTrue(request.validate());
            assertTrue(request.calculateRefundValue()>0);
            assertTrue(request.approve());
            assertTrue(request.receiveReturnedBook(true));
        Payment payment = new Payment("PAY", order, customer, 40, "Card", "2026-01-01");
            payment.processPayment();
        Refund refund = new Refund("RF", payment, request, 20, "2026-01-05", "Original");
        assertTrue(refund.validate());
            assertTrue(refund.usesOriginalMethod());
            assertEquals(20.0, refund.calculateRemainingPayment(), 0.0001);
            assertTrue(refund.process());
            assertEquals("Processed", refund.getStatus());
            assertEquals("Partially Refunded", payment.getPaymentStatus());
            assertEquals(20.0, payment.getRemainingAmount(), 0.0001);

        Discount discount = new Discount("DISC", "Winter", 10, "2026-01-01", "2026-12-31", null);
        assertTrue(discount.isApplicable("2026-03-01", null));
            assertEquals(2.0, discount.calculateDiscount(20), 0.0001);
            assertEquals(18.0, discount.applyToPrice(20), 0.0001);
            assertTrue(discount.changePercentage(15));
            assertTrue(discount.activate());
            discount.deactivate();
        LoyaltyAccount loyalty = new LoyaltyAccount("LA", customer, "2026-01-01");
        assertTrue(loyalty.earnPoints(400));
            assertEquals("Silver", loyalty.getTier());
            assertTrue(loyalty.calculateRewardValue(100)>0);
            assertTrue(loyalty.redeemPoints(100));
            loyalty.expirePoints(10);
            loyalty.recordActivity("2026-02-01");
        Coupon coupon = new Coupon("SAVE", 5, 20, 2, "2026-12-31");
            assertTrue(coupon.isValid("2026-06-01", 30));
            assertEquals(25.0, coupon.apply(30, "2026-06-01"), 0.0001);
            assertEquals(1, coupon.remainingUses());
    }

    @Test
    void extendedWarehouseAndStaffModelsWork() {
        Book book = book("Novel", "ISBN6", 10);
            Employee employee = employee();
            Supplier supplier = supplier();
        WarehouseSection sectionA = new WarehouseSection("A", "A", "A1", 5);
        WarehouseSection sectionB = new WarehouseSection("B", "B", "B1", 5);
        sectionA.addBook(book);
        WarehouseZone zone = new WarehouseZone("Z", "General", "Ambient", 4, false, "Books");
        assertTrue(zone.addSection(sectionA));
            assertEquals(3, zone.availableCapacity());
        Shelf shelf = new Shelf("SH1", zone, "A1", 4, 100, true);
            assertTrue(shelf.storeBook(book));
            assertTrue(shelf.containsIsbn("ISBN6"));
        assertTrue(shelf.canEmployeeAccess(employee));
            assertEquals(3, shelf.availableSlots());
        InventoryTransfer transfer = new InventoryTransfer("T", book, sectionA, sectionB, 1, "2026-01-01");
        assertTrue(transfer.validate());
            assertTrue(transfer.approve());
            assertTrue(transfer.execute());
            assertTrue(transfer.isCompleted());

        SupplierContract contract = new SupplierContract("SC", supplier, "2026-01-01", "2026-12-31", 10, 1000);
        assertTrue(contract.isValidOn("2026-06-01"));
            assertTrue(contract.recordPurchase(100, "2026-06-01"));
        assertEquals(900, contract.remainingLimit());
            assertEquals(90.0, contract.applySupplierDiscount(100), 0.0001);
            assertTrue(contract.renew("2027-12-31"));
        EmployeeShift shift = new EmployeeShift("SHIFT", employee, "2026-01-01", "09:00", "17:00", "Manager");
        assertTrue(shift.validateTimes());
            assertTrue(shift.isAssignedTo(employee));
            assertTrue(shift.startShift("09:00"));
            assertTrue(shift.finishShift());
        EmployeeShift attendanceShift = new EmployeeShift("SHIFT2", employee, "2026-01-02", "09:00", "17:00", "Manager");
        AttendanceRecord attendance = new AttendanceRecord("ATT", employee, attendanceShift);
        assertTrue(attendance.clockIn("09:15"));
            assertTrue(attendance.isLate("09:00"));
            assertTrue(attendance.recordBreak(30));
        assertTrue(attendance.clockOut("17:00"));
            assertEquals(435, attendance.calculateWorkedMinutes());
        BookConditionRecord condition = new BookConditionRecord("BC", book, employee, "2026-01-03", "Good", "Clean copy");
        assertTrue(condition.approveSale());
            assertTrue(condition.classify("Damaged"));
            assertFalse(condition.approveSale());
            condition.addNotes("Cover torn");
        ProcurementPlan plan = new ProcurementPlan("PLAN", supplier, employee, "Q1", 1000);
        RestockRequest request = new RestockRequest("R2", book, supplier, employee, 2, "2026-01-01", "Normal", "Replenish");
        assertTrue(plan.addRequest(request));
            assertEquals(1, plan.countRequests());
            assertTrue(plan.calculateEstimatedSpend()>0);
            assertTrue(plan.isWithinBudget());
            assertTrue(plan.approvePlan());
    }

    @Test
    void edgeCasesCoverLifecycleAndValidationBranches() {
        Book book = book("Edge", "EDGE1", 5);
            Customer customer = customer();
            Supplier supplier = supplier();
            Employee employee = employee();
        BookFormat format = new BookFormat("Digital", 0, 1, 1, true);
            format.updateDimensions(-1, 0);
        BookReview review = new BookReview("REV", book, customer, 8, "", "today");
        assertFalse(review.isValidRating());
            review.updateRating(0);
            review.updateComment("Updated");
        BookReservation reservation = new BookReservation("RES", book, customer, 1, "today", "tomorrow");
        reservation.cancelReservation();
            reservation.cancelReservation();
            assertEquals("Cancelled", reservation.getStatus());

        StockMovement out = new StockMovement("OUT", book, employee, "OUT", 2, "today", "Sale");
        assertTrue(out.applyMovement());
            assertEquals(3, book.getQuantity());
            assertTrue(out.reverseMovement());
        StockMovement invalid = new StockMovement("BAD", book, employee, "OUT", 100, "today", "Bad sale");
        assertFalse(invalid.applyMovement());

        Warehouse warehouse = warehouse(book);
        InventoryAudit audit = new InventoryAudit("EMPTY", warehouse, employee, "today");
        assertFalse(audit.applyStockCorrections());
        assertThrows(InventoryAuditException.class, audit::completeWithoutCorrections);
        assertTrue(audit.recordPhysicalCount(book, 5));
            audit.completeWithoutCorrections();
            assertTrue(audit.reopen());

        PurchaseOrder cancellable = new PurchaseOrder("CANCEL", supplier, "today", "tomorrow");
        assertFalse(cancellable.confirmOrder());
            assertTrue(cancellable.addBook(book, 1));
        assertTrue(cancellable.removeBook(book));
            assertTrue(cancellable.cancelOrder());
        assertFalse(cancellable.addBook(book, 1));
        PurchaseOrder rejectPo = new PurchaseOrder("REJPO", supplier, "today", "tomorrow");
        Delivery rejected = new Delivery("REJ", supplier, employee, rejectPo, "tomorrow", "TRACK");
        assertTrue(rejected.rejectDelivery("Damaged packaging"));
            assertEquals("Rejected", rejected.getDeliveryStatus());
        rejected.updateTrackingNumber("NEW");
            rejected.updateDeliveryDate("later");

        RestockRequest rejectedRequest = new RestockRequest("REJREQ", book, supplier, employee, 2, "today", "Low", "Need stock");
        assertTrue(rejectedRequest.rejectRequest("Budget unavailable"));
            assertFalse(rejectedRequest.approveRequest());
        RestockRequest cancelledRequest = new RestockRequest("CANREQ", book, supplier, employee, 2, "today", "Normal", "Routine");
        assertTrue(cancelledRequest.cancelRequest());
            assertFalse(cancelledRequest.cancelRequest());

        StockAlert critical = new StockAlert("CRIT", book, 100, "today");
        assertEquals("High", critical.determineSeverity());
        book.removeStock(5);
            assertEquals("Critical", critical.determineSeverity());
        critical.updateMinimumStockLevel(-1);
            critical.updateAlertMessage("Review urgently");

        Invoice emptyInvoice = new Invoice("EMPTYINV", new Order("O", customer, "today"), customer, "today", "tomorrow", 5);
        assertFalse(emptyInvoice.issue());
            assertFalse(emptyInvoice.markPaid());
            assertFalse(emptyInvoice.applyDiscount(-1));
        assertTrue(emptyInvoice.cancel());
            assertFalse(emptyInvoice.cancel());
        InvoiceLine line = new InvoiceLine("IL", book, 1, 10, 0, "line");
        assertFalse(line.updateQuantity(0));
            assertFalse(line.updateUnitPrice(-1));

        Shipment emptyShipment = new Shipment("EMPTYSH", new Order("O2", customer, "today"), null, "Carrier", "T", "today");
        assertFalse(emptyShipment.dispatch());
            assertFalse(emptyShipment.addItem(null));
        assertTrue(emptyShipment.reportIssue("Address problem"));
        ShipmentItem shipmentItem = new ShipmentItem("SI2", book, 1, 10, "Standard");
        assertFalse(shipmentItem.changeQuantity(0));
            assertFalse(shipmentItem.requiresSpecialPackaging());

        ReturnRequest returnRequest = new ReturnRequest("RRET", new Order("O3", customer, "today"), customer, book, 1, "today",
            "Unopened book");
        assertTrue(returnRequest.isResellableReason());
            assertTrue(returnRequest.reject("Outside return period"));
        assertFalse(returnRequest.receiveReturnedBook(true));
        Payment payment = new Payment("PAY2", new Order("O4", customer, "today"), customer, 10, "Card", "today");
        Refund refund = new Refund("RF2", payment, returnRequest, 5, "today", "Original");
        assertFalse(refund.validate());
            assertTrue(refund.cancel());
            assertFalse(refund.process());

        Discount discount = new Discount("D2", "Sale", 10, "2026-01-01", "2026-01-31", "Fiction");
        assertFalse(discount.isApplicable("2026-02-01", null));
            assertFalse(discount.changePercentage(101));
        LoyaltyAccount account = new LoyaltyAccount("LA2", customer, "today");
        assertFalse(account.earnPoints(0));
            assertFalse(account.redeemPoints(1));
            account.expirePoints(5);
        Coupon coupon = new Coupon("C2", 5, 20, 1, "2026-01-31");
        assertEquals(30.0, coupon.apply(30, "2026-02-01"), 0.0001);
            coupon.disable();
            assertFalse(coupon.isValid("2026-01-01", 30));
        assertFalse(coupon.extendExpiry("2025-01-01"));

        WarehouseZone zone = new WarehouseZone("Z2", "Cold", "2-8C", 1, true, "Special storage");
        assertTrue(zone.canStoreTemperatureSensitiveGoods());
            WarehouseSection zoneSection = new WarehouseSection("ZSEC", "Zone", "Z1", 2);
            assertTrue(zone.addSection(zoneSection));
            assertTrue(zone.removeSection(zoneSection));
            assertTrue(zone.addSection(zoneSection));
        assertFalse(zone.addSection(new WarehouseSection("ZSEC2", "Zone2", "Z2", 2)));
            zone.changePurpose("Archive");
        Shelf shelf = new Shelf("S2", zone, "B2", 1, 50, false);
        assertFalse(shelf.canEmployeeAccess(employee));
            assertTrue(shelf.storeBook(book));
            assertFalse(shelf.storeBook(book));
            assertTrue(shelf.removeBook(book));
        InventoryTransfer badTransfer = new InventoryTransfer("BADT", book, new WarehouseSection("S1", "S1", "1", 1),
            new WarehouseSection("S2", "S2", "2", 1), 1, "today");
        assertFalse(badTransfer.validate());
            assertTrue(badTransfer.cancel());
        SupplierContract contract = new SupplierContract("SC2", supplier, "2026-01-01", "2026-12-31", 5, 10);
        assertFalse(contract.recordPurchase(20, "2026-06-01"));
            contract.suspend();
            assertFalse(contract.isValidOn("2026-06-01"));
        EmployeeShift shift = new EmployeeShift("SHIFT3", employee, "today", "17:00", "09:00", "Clerk");
        assertFalse(shift.validateTimes());
            assertTrue(shift.cancelShift());
        AttendanceRecord attendance = new AttendanceRecord("ATT2", employee, shift);
        assertFalse(attendance.clockOut("17:00"));
            assertFalse(attendance.clockIn(""));
            assertFalse(attendance.recordBreak(500));
        BookConditionRecord condition = new BookConditionRecord("BC2", book, employee, "today", "Good", "Fine");
        assertFalse(condition.classify("Unknown"));
            assertTrue(condition.classify("Unsellable"));
            assertTrue(condition.requiresRemoval());
        ProcurementPlan plan = new ProcurementPlan("PLAN2", supplier, employee, "Q2", 1);
        assertFalse(plan.approvePlan());
            assertTrue(plan.cancelPlan());
    }

    @Test
    void identifiersAssociationsAndRemainingBranchesAreCovered() {
        Author author = new Author("A", "N", 1980, "Bio");
            assertEquals("A", author.getName());
        BookFormat format = new BookFormat("Hardcover", 0.5, 20, 15, false);
            assertEquals("Hardcover", format.getFormatName());
        Customer customer = customer();
            assertEquals("C1", customer.getCustomerId());
            assertEquals("Sam Customer", customer.getFullName());
        Employee employee = employee();
            assertEquals("E1", employee.getEmployeeId());
            assertEquals("Manager", employee.getPosition());
        Book book = book("Access", "ACCESS1", 8);
        Supplier supplier = supplier();
            assertEquals("S1", supplier.getSupplierId());
            assertEquals("Book Supply Ltd", supplier.getCompanyName());
        Order order = new Order("OA", customer, "today");
            order.addBook(book, 1);
            assertEquals("OA", order.getOrderId());
        Payment payment = new Payment("PA", order, customer, 20, "Card", "today");
            assertEquals(20.0, payment.getAmount(), 0.0001);
        BookReview review = new BookReview("RA", book, customer, 4, "Good", "today");
        assertEquals("RA", review.getReviewId());
            assertEquals(4, review.getRating());
            review.markHelpful();
            assertEquals(1, review.getHelpfulVotes());
        BookReservation reservation = new BookReservation("RSA", book, customer, 1, "today", "tomorrow");
            assertEquals("RSA", reservation.getReservationId());
        WarehouseSection section = new WarehouseSection("SEC-A", "A", "A1", 3);
            assertEquals("SEC-A", section.getSectionId());
            assertEquals("A", section.getSectionName());
        InventoryReport report = new InventoryReport("R-A", warehouse(book), "today", "Daily");
            assertEquals("R-A", report.getReportId());
        StockMovement movement = new StockMovement("M-A", book, employee, "IN", 1, "today", "restock");
            assertEquals("M-A", movement.getMovementId());
        PurchaseOrder purchaseOrder = new PurchaseOrder("PO-A", supplier, "today", "tomorrow");
            assertEquals("PO-A", purchaseOrder.getPurchaseOrderId());
        RestockRequest restock = new RestockRequest("RR-A", book, supplier, employee, 1, "today", "Normal", "Routine");
            assertEquals("RR-A", restock.getRequestId());
        StockAlert alert = new StockAlert("AL-A", book, 2, "today");
            assertEquals("AL-A", alert.getAlertId());
        Delivery delivery = new Delivery("D-A", supplier, employee, purchaseOrder, "tomorrow", "TRACK-A");
            assertEquals("D-A", delivery.getDeliveryId());
            delivery.updateTrackingNumber("TRACK-B");
            delivery.updateDeliveryDate("later");
        Invoice invoice = new Invoice("I-A", order, customer, "today", "tomorrow", 0);
            assertEquals("I-A", invoice.getInvoiceId());
        InvoiceLine invoiceLine = new InvoiceLine("IL-A", book, 1, 20, 0, "copy");
            assertSame(book, invoiceLine.getBook());
        ShipmentItem shipmentItem = new ShipmentItem("SI-A", book, 1, 20, "Standard");
            assertEquals(1, shipmentItem.getQuantity());
            assertSame(book, shipmentItem.getBook());
        Shipment shipment = new Shipment("SH-A", order, delivery, "Carrier", "TR", "today");
            assertEquals("Prepared", shipment.getStatus());
        Discount discount = new Discount("DISC-A", "A", 5, "2026-01-01", "2026-12-31", null);
            assertEquals("DISC-A", discount.getDiscountId());
        LoyaltyAccount loyalty = new LoyaltyAccount("LOY-A", customer, "today");
            assertEquals(0, loyalty.getPointsBalance());
            assertEquals("Bronze", loyalty.getTier());
        Coupon coupon = new Coupon("COUP-A", 2, 10, 2, "2026-12-31");
            assertEquals("COUP-A", coupon.getCode());
        WarehouseZone zone = new WarehouseZone("Z-A", "A", "Ambient", 2, false, "Storage");
            assertEquals(0, zone.sectionCount());
        Shelf shelf = new Shelf("S-A", zone, "A1", 2, 50, true);
            assertEquals(0, shelf.bookCount());
        InventoryTransfer transfer = new InventoryTransfer("T-A", book, section, new WarehouseSection("D-A", "D", "D1", 2), 1, "today");
            assertEquals("Requested", transfer.getStatus());
        SupplierContract contract = new SupplierContract("SC-A", supplier, "2026-01-01", "2026-12-31", 5, 100);
            assertSame(supplier, contract.getSupplier());
        EmployeeShift shift = new EmployeeShift("ES-A", employee, "today", "08:00", "16:00", "Manager");
            assertEquals("Scheduled", shift.getStatus());
        AttendanceRecord attendance = new AttendanceRecord("AR-A", employee, shift);
            assertSame(employee, attendance.getEmployee());
        BookConditionRecord condition = new BookConditionRecord("BC-A", book, employee, "today", "Good", "OK");
            assertEquals("Good", condition.getCondition());
        ProcurementPlan plan = new ProcurementPlan("PP-A", supplier, employee, "Q1", 100);
            assertEquals("Draft", plan.getStatus());
        InventoryAudit audit = new InventoryAudit("AUD-A", warehouse(book), employee, "today");
            assertEquals("In Progress", audit.getAuditStatus());
            assertEquals(0, audit.getTotalDiscrepancies());
        assertEquals("Press", new Publisher("Press", "City", "p@example.com", "123", "Country").getName());
        Throwable cause = new IllegalStateException("cause");
        assertNotNull(new BookWarehouseException("base", cause).getCause());
        assertNotNull(new BookNotFoundException("missing", cause).getCause());
        assertNotNull(new InvalidBookDataException("invalid", cause).getCause());
        assertNotNull(new DuplicateIsbnException("duplicate", cause).getCause());
        assertNotNull(new InsufficientStockException("stock", cause).getCause());
        assertNotNull(new CustomerNotFoundException("customer", cause).getCause());
        assertNotNull(new SupplierNotFoundException("supplier", cause).getCause());
        assertNotNull(new InvalidOrderStateException("state", cause).getCause());
        assertNotNull(new PaymentProcessingException("payment", cause).getCause());
        assertNotNull(new DeliveryProcessingException("delivery", cause).getCause());
        assertNotNull(new CapacityExceededException("capacity", cause).getCause());
        assertNotNull(new InventoryAuditException("audit", cause).getCause());
        assertEquals("Pending", delivery.getDeliveryStatus());
    }

    @Test
    void serviceUsesDomainExceptionsAndCliRuns() {
        Book book = book("Novel", "ISBN7", 10);
            Warehouse warehouse = warehouse(book);
        WarehouseService service = new WarehouseService(warehouse);
        assertSame(book, service.findBook("ISBN7"));
        assertThrows(BookNotFoundException.class, () -> service.findBook("missing"));
        assertThrows(DuplicateIsbnException.class, () -> service.registerBook(book));
        assertThrows(InvalidBookDataException.class, () -> service.registerBook(null));
        Customer customer = customer();
            service.registerCustomer(customer);
        Order order = service.placeOrder("ORD", "C1", "ISBN7", 2, "2026-01-01");
            assertNotNull(order);
        assertThrows(CustomerNotFoundException.class, () -> service.placeOrder("ORD2", "missing", "ISBN7", 1, "2026-01-01"));
        assertThrows(InsufficientStockException.class, () -> service.receiveStock("ISBN7", 0));
        assertThrows(InsufficientStockException.class, () -> service.placeOrder("ORD3", "C1", "ISBN7", 100, "2026-01-01"));
        assertThrows(InvalidOrderStateException.class, () -> service.placeOrder("ORD", "C1", "ISBN7", 1, "2026-01-01"));
        Payment validPayment = new Payment("SP1", order, customer, 40, "Card", "2026-01-01");
        service.processPayment(validPayment);
        assertTrue(validPayment.isPaymentSuccessful());
        assertThrows(PaymentProcessingException.class, () -> service.processPayment(new Payment("SP2", order, customer, 0, "Card",
            "2026-01-01")));
        Supplier supplier = supplier();
            service.registerSupplier(supplier);
        PurchaseOrder pendingOrder = new PurchaseOrder("PENDING", supplier, "2026-01-01", "2026-01-02");
        Delivery invalidDelivery = new Delivery("D-INVALID", supplier, employee(), pendingOrder, "2026-01-02", "T-INVALID");
        assertThrows(DeliveryProcessingException.class, () -> service.receiveDelivery(invalidDelivery));
        PurchaseOrder servicePo = new PurchaseOrder("SERVICE-PO", supplier, "2026-01-01", "2026-01-02");
        servicePo.addBook(book, 1);
            servicePo.confirmOrder();
        Delivery serviceDelivery = new Delivery("SERVICE-D", supplier, employee(), servicePo, "2026-01-02", "SERVICE-TRACK");
        service.receiveDelivery(serviceDelivery);
        WarehouseSection fullSection = new WarehouseSection("FULL", "Full", "F1", 1);
        fullSection.addBook(book);
        assertThrows(CapacityExceededException.class, () -> service.addBookToSection(fullSection, book("Extra", "ISBN8", 1)));
        assertThrows(InventoryAuditException.class, () -> service.addBookToSection(null, book));
        service.addBookToSection(new WarehouseSection("OPEN", "Open", "O1", 2), book);
        assertThrows(BookWarehouseException.class, () -> { throw new BookWarehouseException("base domain error");
            });
        assertSame(supplier, service.requireSupplier("S1"));
        assertThrows(SupplierNotFoundException.class, () -> service.requireSupplier("missing"));
        service.receiveStock("ISBN7", 1);
            assertEquals(10, book.getQuantity());
        assertEquals(1, service.titleCount());
            assertTrue(service.inventoryValue()>0);
        service.removeBook("ISBN7");
            assertEquals(0, service.titleCount());
        WarehouseCli cli = WarehouseCli.createDemoCli(new Scanner("6\n7\n"));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream previous = System.out;
        try { System.setOut(new PrintStream(output));
            cli.run();
            }
        finally { System.setOut(previous);
            }
        assertTrue(output.toString().contains("Inventory value"));
        WarehouseCli errorCli = WarehouseCli.createDemoCli(new Scanner("8\n2\nnot-found\n7\n"));
        ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();
        try { System.setOut(new PrintStream(errorOutput));
            errorCli.run();
            }
        finally { System.setOut(previous);
            }
        assertTrue(errorOutput.toString().contains("Operation failed"));
        WarehouseCli interactiveCli = WarehouseCli.createDemoCli(new Scanner("2\n9780385474542\n3\n9780385474542\n2\n6\n7\n"));
        ByteArrayOutputStream interactionOutput = new ByteArrayOutputStream();
        try { System.setOut(new PrintStream(interactionOutput));
            interactiveCli.run();
            }
        finally { System.setOut(previous);
            }
        assertTrue(interactionOutput.toString().contains("Stock received successfully"));
        String cliInput = "4\nCLI Book\nCLI-ISBN\nEnglish\n2025\n100\n12.5\n4\n2\nCLI-ISBN\n3\nCLI-ISBN\n2\n5\nCLI-ISBN\n6\n7\n";
        WarehouseCli fullCli = WarehouseCli.createDemoCli(new Scanner(cliInput));
        ByteArrayOutputStream fullOutput = new ByteArrayOutputStream();
        try { System.setOut(new PrintStream(fullOutput));
            fullCli.run();
            }
        finally { System.setOut(previous);
            }
        assertTrue(fullOutput.toString().contains("Book registered successfully"));
        assertTrue(fullOutput.toString().contains("Book removed successfully"));
        java.io.InputStream originalInput = System.in;
        try {
            System.setIn(new java.io.ByteArrayInputStream("7\n".getBytes(java.nio.charset.StandardCharsets.UTF_8)));
            Main.main(new String[0]);
        } finally {
            System.setIn(originalInput);
        }
    }
}
