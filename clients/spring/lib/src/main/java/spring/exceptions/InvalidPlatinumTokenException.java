package spring.exceptions;

public class InvalidPlatinumTokenException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Invalid platinum token!";
    }
}
