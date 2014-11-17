package model.exception;

public class InvalidCartQuantityException extends AppException {
    public InvalidCartQuantityException() {
        this("Cart quantity cannot be negative.");
    }
    public InvalidCartQuantityException(String msg) {
        super(msg);
    }
    public InvalidCartQuantityException(NumberFormatException e) {
        super("Cart quantity is invalid integer value.", e);
    }
}
