package exceptions;

public class InvalidBookDataException extends BookWarehouseException {
    private static final long serialVersionUID = 1L;

    public InvalidBookDataException(String message) {
        super(message);
    }

    public InvalidBookDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
