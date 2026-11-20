package models;

public class ReturnRequest {
    private String returnId;
    private Order order;
    private Customer customer;
    private Book book;
    private int quantity;
    private String requestDate;
    private String reason;
    private String status;

    public ReturnRequest(String returnId, Order order, Customer customer, Book book, int quantity, String requestDate, String reason) {
        this.returnId=returnId; this.order=order; this.customer=customer; this.book=book; this.quantity=quantity;
        this.requestDate=requestDate; this.reason=reason; this.status="Submitted";
    }
    public boolean validate() { return returnId!=null && order!=null && customer!=null && book!=null && quantity>0 &&
        requestDate!=null && reason!=null && !reason.isBlank(); }
    public boolean approve() {
        if(!validate() || !"Submitted".equals(status)) return false;
        status="Approved";
        return true;
    }
    public boolean reject(String note) {
        if(!"Submitted".equals(status) || note==null || note.isBlank()) return false;
        reason=note;
        status="Rejected";
        return true;
    }
    public boolean receiveReturnedBook(boolean resellable) {
        if(!"Approved".equals(status)) return false;
        if(resellable) book.addStock(quantity);
        status="Received";
        return true;
    }
    public double calculateRefundValue() { return book==null ? 0 : book.getPrice()*quantity; }
    public boolean isResellableReason() { return reason!=null && (reason.toLowerCase().contains("unopened") ||
        reason.toLowerCase().contains("wrong")); }
    public String getStatus() { return status; }
}
