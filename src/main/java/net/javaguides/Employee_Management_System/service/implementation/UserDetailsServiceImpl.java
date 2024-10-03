package net.javaguides.Employee_Management_System.service.implementation;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.entity.Employee;
import net.javaguides.Employee_Management_System.entity.Role;
import net.javaguides.Employee_Management_System.exception.EmployeeNotFoundException;
import net.javaguides.Employee_Management_System.repository.EmployeeRepository;
import net.javaguides.Employee_Management_System.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private MessageService messageService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

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
                .map(Role::getName)
                .toArray(String[]::new);

        for(String roleName : roleNames) {
            logger.info("role name: {}", roleName);
        }

        return new org.springframework.security.core.userdetails.User(
                employee.getEmail(), employee.getPassword(), true, true, true,
                true, getAuthorities(employee.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(roles);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Collection<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
