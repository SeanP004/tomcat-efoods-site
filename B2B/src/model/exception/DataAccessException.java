package model.exception;

public class DataAccessException extends AppException {
    public DataAccessException() {}
    public DataAccessException(String msg, Exception e) {
        super(msg, e);
    }
    public DataAccessException(Exception e) {
        this("Could not execute data access request.", e);
    }
}
