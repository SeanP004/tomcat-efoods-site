package model.exception;

public class InvalidBulkUpdateException extends AppException {
    public InvalidBulkUpdateException() {
        this("Invalid bulk update.");
    }
    public InvalidBulkUpdateException(String msg) {
        super(msg);
    }
    public InvalidBulkUpdateException(NumberFormatException e) {
        super("Invalid bulk update", e);
    }
}
