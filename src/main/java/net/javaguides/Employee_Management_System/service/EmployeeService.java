package net.javaguides.Employee_Management_System.service;

import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(Long id);

    EmployeeDto assignRoleToEmployee(Long employeeId, Long roleId);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(long employeeId, EmployeeDto updatedEmployeeDto);

    void deleteEmployee(long employeeId);
}
