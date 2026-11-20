package exceptions;

public class DeliveryProcessingException extends BookWarehouseException {
    private static final long serialVersionUID = 1L;

    public DeliveryProcessingException(String message) {
        super(message);
    }

    public DeliveryProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
