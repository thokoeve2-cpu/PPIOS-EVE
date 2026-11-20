package exceptions;

public class BookWarehouseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BookWarehouseException(String message) {
        super(message);
    }

    public BookWarehouseException(String message, Throwable cause) {
        super(message, cause);
    }
}
