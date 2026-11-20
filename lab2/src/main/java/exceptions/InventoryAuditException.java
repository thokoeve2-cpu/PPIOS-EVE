package exceptions;

public class InventoryAuditException extends BookWarehouseException {
    private static final long serialVersionUID = 1L;

    public InventoryAuditException(String message) {
        super(message);
    }

    public InventoryAuditException(String message, Throwable cause) {
        super(message, cause);
    }
}
