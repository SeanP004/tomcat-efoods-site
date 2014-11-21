package model.exception;

public class OrderCheckoutException extends AppException {
    public OrderCheckoutException(Exception e) {
        super(e);
    }
    public OrderCheckoutException(String msg) {
        super(msg);
    }
}
