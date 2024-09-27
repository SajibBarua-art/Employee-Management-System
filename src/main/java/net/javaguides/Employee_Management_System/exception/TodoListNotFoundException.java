package net.javaguides.Employee_Management_System.exception;

public class TodoListNotFoundException extends RuntimeException{
    public TodoListNotFoundException(String message){
        super(message);
    }
}
