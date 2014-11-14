package model.exception;

public class DAOCreationException extends AppException {
    public DAOCreationException() {}
    public DAOCreationException(Exception e) {
        super("Could not create data access handler.", e);
    }
}
