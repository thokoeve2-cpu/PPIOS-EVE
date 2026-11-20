package exceptions;

public class DuplicateIsbnException extends BookWarehouseException {
    private static final long serialVersionUID = 1L;

    public DuplicateIsbnException(String message) {
        super(message);
    }

    public DuplicateIsbnException(String message, Throwable cause) {
        super(message, cause);
    }
}
