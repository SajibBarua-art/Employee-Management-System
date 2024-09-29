package net.javaguides.Employee_Management_System.controller;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.controller.advice.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // this class is capable to handle http request
@RequestMapping("/api/public") // to define the base url of all the rest APIs
@AllArgsConstructor
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
}
