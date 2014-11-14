package model.exception;

public class DataAccessException extends AppException {
    public DataAccessException() {}
    public DataAccessException(Exception e) {
        super("Could not execute data access request.", e);
    }
}
