package net.javaguides.Employee_Management_System.exception;

public class PasswordNotMatchedException extends RuntimeException {
    public PasswordNotMatchedException(String message) {
        super(message);
    }
}
