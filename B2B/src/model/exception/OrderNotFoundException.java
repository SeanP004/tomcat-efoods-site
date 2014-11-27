package model.exception;

public class OrderNotFoundException extends AppException {
    public OrderNotFoundException() {
        super("Order does not exist.");
    }
    public OrderNotFoundException(String msg) {
        super(msg);
    }
}
