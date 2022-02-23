package spring.exceptions;

public class ExpiredGoldenTokenException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Expired golden token!";
    }
}
