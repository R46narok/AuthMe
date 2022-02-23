package spring.exceptions;

public class InvalidGoldenTokenException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Invalid golden token!";
    }
}
