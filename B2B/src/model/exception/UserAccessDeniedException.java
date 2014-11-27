package model.exception;

public class UserAccessDeniedException extends AppException {
    public UserAccessDeniedException() {
        this("Cannot access a logged out user's account info.");
    }
    public UserAccessDeniedException(String msg) {
        super(msg);
    }
}
