package spring.exceptions;

public class NoPermissionException extends RuntimeException {
    @Override
    public String getMessage() {
        return "No permissions for the requested fields!";
    }
}
