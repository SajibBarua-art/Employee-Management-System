package net.javaguides.Employee_Management_System.exception;

public class RequestBodyFormatNotMatchedException extends RuntimeException {
    public RequestBodyFormatNotMatchedException(String message) {
        super(message);
    }
}
