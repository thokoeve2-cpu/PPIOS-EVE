package exceptions;

public class PaymentProcessingException extends BookWarehouseException {
    private static final long serialVersionUID = 1L;

    public PaymentProcessingException(String message) {
        super(message);
    }

    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
