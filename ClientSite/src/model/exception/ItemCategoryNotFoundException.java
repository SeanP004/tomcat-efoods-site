package model.exception;

public class ItemCategoryNotFoundException extends AppException {
    public ItemCategoryNotFoundException() {
        this("Could not found item category in database.");
    }
    public ItemCategoryNotFoundException(String msg) {
        super(msg);
    }
}
