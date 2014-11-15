package model.exception;

public class AppException extends RuntimeException {
    public AppException() {}
    public AppException(String msg, Exception e) {
        super(msg, e);
    }
    public AppException(String msg) {
        super(msg);
    }
}
