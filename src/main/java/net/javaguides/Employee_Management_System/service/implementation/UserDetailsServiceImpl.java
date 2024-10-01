package net.javaguides.Employee_Management_System.service.implementation;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.entity.Employee;
import net.javaguides.Employee_Management_System.entity.Role;
import net.javaguides.Employee_Management_System.exception.EmployeeNotFoundException;
import net.javaguides.Employee_Management_System.repository.EmployeeRepository;
import net.javaguides.Employee_Management_System.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private MessageService messageService;

    @Override
    public UserDetails loadUserByUsername(String email) throws EmployeeNotFoundException {
        // Step 1: Fetch employee by email
        Employee employee = employeeRepository.findByEmail(email);

        // Step 2: If employee is not found, throw an exception
        if (employee == null) {
            throw new EmployeeNotFoundException(messageService.getMessage("employee.notfound", email));
        }

        // Step 3: Get roles from employee and convert them to a String array
        Set<Role> roles = employee.getRoles();
        String[] roleNames = roles.stream()
                .map(Role::getName)  // Assuming Role class has a getName() method
                .toArray(String[]::new);

        // Step 4: Build the UserDetails object for Spring Security
        return org.springframework.security.core.userdetails.User.builder()
                .username(employee.getEmail())      // Or employee.getUserName() if you have a username field
                .password(employee.getPassword())   // The hashed password
                .roles(roleNames)                   // The roles (e.g., "ADMIN", "USER")
                .build();
    }
}
