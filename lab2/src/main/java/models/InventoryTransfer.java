package models;

public class InventoryTransfer {
    private String transferId;
    private Book book;
    private WarehouseSection source;
    private WarehouseSection destination;
    private int quantity;
    private String requestedDate;
    private String status;

    public InventoryTransfer(String id,Book book,WarehouseSection source,WarehouseSection destination,int quantity,String date) {
        this.transferId=id;
        this.book=book;
        this.source=source;
        this.destination=destination;
        this.quantity=quantity;
        this.requestedDate=date;
        this.status="Requested";
    }
    public boolean validate() { return book!=null && source!=null && destination!=null && source!=destination &&
        quantity>0 && source.containsBook(book); }
    public boolean approve() {
        if(!validate() || !"Requested".equals(status)) return false;
        status="Approved";
        return true;
    }
    public boolean execute() {
        if(!"Approved".equals(status) || !source.containsBook(book) || destination.isFull()) return false;
        source.removeBook(book);
        destination.addBook(book);
        status="Completed";
        return true;
    }
    public boolean cancel() {
        if("Completed".equals(status)) return false;
        status="Cancelled";
        return true;
    }
    public boolean isCompleted() { return "Completed".equals(status); }
    public String getStatus() { return status; }
}
