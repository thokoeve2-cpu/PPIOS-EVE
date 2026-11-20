package models;

public class BookConditionRecord {
    private String inspectionId;
    private Book book;
    private Employee inspector;
    private String inspectionDate;
    private String condition;
    private String notes;
    private boolean approvedForSale;

    public BookConditionRecord(String id,Book book,Employee inspector,String date,String condition,String notes) {
        this.inspectionId=id;
        this.book=book;
        this.inspector=inspector;
        this.inspectionDate=date;
        this.condition=condition;
        this.notes=notes;
        this.approvedForSale="New".equalsIgnoreCase(condition)||"Good".equalsIgnoreCase(condition);
    }
    public boolean classify(String value) {
        if (value == null || !(value.equalsIgnoreCase("New")
                || value.equalsIgnoreCase("Good")
                || value.equalsIgnoreCase("Damaged")
                || value.equalsIgnoreCase("Unsellable"))) {
            return false;
        }
        condition=value;
        approvedForSale=value.equalsIgnoreCase("New")||value.equalsIgnoreCase("Good");
        return true;
    }
    public boolean approveSale() {
        if(!approvedForSale || book==null || book.getQuantity()<=0) return false;
        return true;
    }
    public void addNotes(String value) {
        if (value != null && !value.isBlank()) {
            notes = notes == null ? value : notes + "; " + value;
        }
    }
    public boolean requiresRemoval() { return "Unsellable".equalsIgnoreCase(condition); }
    public String getCondition() { return condition; }
}
