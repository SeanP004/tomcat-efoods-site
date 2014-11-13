package model.exception;

public class DataAccessException extends Exception {
    public DataAccessException() {}
    public DataAccessException(String msg) {
        super(msg);
    }
}
