package model.exception;

public class CategoryNotFoundException extends AppException {
    public CategoryNotFoundException() {
        this("Could not found category in database.");
    }
    public CategoryNotFoundException(String msg) {
        super(msg);
    }
}
