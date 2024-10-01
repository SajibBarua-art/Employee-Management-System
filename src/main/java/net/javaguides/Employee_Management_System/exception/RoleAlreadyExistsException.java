package net.javaguides.Employee_Management_System.exception;

public class RoleAlreadyExistsException extends RuntimeException{
    public RoleAlreadyExistsException(String message) {
        super(message);
    }
}
