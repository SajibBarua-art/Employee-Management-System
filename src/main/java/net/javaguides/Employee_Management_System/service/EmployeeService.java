package net.javaguides.Employee_Management_System.service;

import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(long id);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(long employeeId, EmployeeDto updatedEmployeeDto);

    void deleteEmployee(long employeeId);
}
