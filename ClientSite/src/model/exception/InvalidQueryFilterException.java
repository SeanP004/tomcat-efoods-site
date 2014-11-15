package model.exception;

public class InvalidQueryFilterException extends AppException {
    public InvalidQueryFilterException() {
        this("One or more of the given arguments for"
                + "the filter has an invalid value.");
    }

    public InvalidQueryFilterException(String msg) {
        super(msg);
    }

    public InvalidQueryFilterException(String msg, Exception e) {
        super(msg, e);
    }
}
