package exceptions;

public class SupplierNotFoundException extends BookWarehouseException {
    private static final long serialVersionUID = 1L;

    public SupplierNotFoundException(String message) {
        super(message);
    }

    public SupplierNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
