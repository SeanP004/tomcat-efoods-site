package model.exception;

public class ItemNotFoundException extends AppException {
    public ItemNotFoundException() {
        this("Could not found item in database.");
    }
    public ItemNotFoundException(String msg) {
        super(msg);
    }
}
