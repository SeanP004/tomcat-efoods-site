package model.exception;

public class AppException extends Exception {
    public AppException() {}
    public AppException(String msg, Exception e) {
        super(msg, e);
    }
}
