package net.javaguides.Employee_Management_System.controller;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.controller.advice.GlobalExceptionHandler;
import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import net.javaguides.Employee_Management_System.dto.EmployeeRequestDto;
import net.javaguides.Employee_Management_System.entity.Employee;
import net.javaguides.Employee_Management_System.service.EmployeeService;
import net.javaguides.Employee_Management_System.service.implementation.UserDetailsServiceImpl;
import net.javaguides.Employee_Management_System.utilis.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController // this class is capable to handle http request
@RequestMapping("/api/public") // to define the base url of all the rest APIs
@AllArgsConstructor
public class PublicController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/signup")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
        // @RequestBody: Extract the json from the http request and
        // convert that json into EmployeeDto java object
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeRequestDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody EmployeeRequestDto employeeRequestDto) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(employeeRequestDto.getEmail(), employeeRequestDto.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(employeeRequestDto.getEmail());

            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }
}
