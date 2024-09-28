package net.javaguides.Employee_Management_System.controller.advice;

import net.javaguides.Employee_Management_System.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    Map<String, Object> buildError(String message, Integer statusCode, String shortErrorMessage) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", message);
        errorDetails.put("status", statusCode);
        errorDetails.put("error", shortErrorMessage);
        return errorDetails;
    }

    @ExceptionHandler({EmployeeNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleEmployeeNotFoundException(EmployeeNotFoundException e){
        logger.error("Handling EmployeeNotFoundException: {}", e.getMessage());

        Map<String, Object> errorDetails = buildError(e.getMessage(), HttpStatus.NOT_FOUND.value(), "Employee Not Found!");

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EmailAlreadyExistsException.class})
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyExistsException(EmailAlreadyExistsException e){
        logger.error("Handling EmailAlreadyExistsException: {}", e.getMessage());

        Map<String, Object> errorDetails = buildError(e.getMessage(), HttpStatus.CONFLICT.value(), "Email Already Exists!");

        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ProjectNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleProjectNotFoundException(ProjectNotFoundException e){
        logger.error("Handling ProjectNotFoundException: {}", e.getMessage());

        Map<String, Object> errorDetails = buildError(e.getMessage(), HttpStatus.NOT_FOUND.value(), "Project Not Found!");

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DesignationNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleDesignationNotFoundException(DesignationNotFoundException e){
        logger.error("Handling DesignationNotFoundException: {}", e.getMessage());

        Map<String, Object> errorDetails = buildError(e.getMessage(), HttpStatus.NOT_FOUND.value(), "Designation Not Found");

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({TodoListNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleTodoListNotFoundException(TodoListNotFoundException e){
        logger.error("Handling TodoListNotFoundException: {}", e.getMessage());

        Map<String, Object> errorDetails = buildError(e.getMessage(), HttpStatus.NOT_FOUND.value(), "Todo List Not Found");

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RoleNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleRoleNotFoundException(RoleNotFoundException e){
        logger.error("Handling RoleNotFoundException: {}", e.getMessage());

        Map<String, Object> errorDetails = buildError(e.getMessage(), HttpStatus.NOT_FOUND.value(), "Role Not Found");

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}
