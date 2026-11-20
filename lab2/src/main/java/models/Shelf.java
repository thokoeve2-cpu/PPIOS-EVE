package models;

import java.util.ArrayList;
import java.util.List;

public class Shelf {
    private String shelfId;
    private WarehouseZone zone;
    private String aisleCode;
    private int capacity;
    private int weightLimit;
    private boolean accessible;
    private final List<Book> books=new ArrayList<>();

    public Shelf(String id,WarehouseZone zone,String aisleCode,int capacity,int weightLimit,boolean accessible) {
        this.shelfId=id;
        this.zone=zone;
        this.aisleCode=aisleCode;
        this.capacity=capacity;
        this.weightLimit=weightLimit;
        this.accessible=accessible;
    }
    public boolean storeBook(Book book) {
        if(book==null || books.size()>=capacity || books.contains(book)) return false;
        books.add(book);
        return true;
    }
    public boolean removeBook(Book book) { return books.remove(book); }
    public boolean containsIsbn(String isbn) { return books.stream().anyMatch(b->b.getIsbn().equals(isbn)); }
    public int availableSlots() { return Math.max(0,capacity-books.size()); }
    public boolean canEmployeeAccess(Employee employee) { return accessible && employee!=null; }
    public int bookCount() { return books.size(); }
}
