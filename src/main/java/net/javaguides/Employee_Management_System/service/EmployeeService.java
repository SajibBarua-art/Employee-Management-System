package net.javaguides.Employee_Management_System.service;

import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import net.javaguides.Employee_Management_System.dto.EmployeeRequestDto;
import net.javaguides.Employee_Management_System.entity.Employee;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeRequestDto employeeRequestDto);

    EmployeeDto getEmployee();
    EmployeeDto getEmployeeById(Long id);

    EmployeeDto assignRoleToEmployee(Long employeeId, Long roleId);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(EmployeeRequestDto employeeRequestDto);

    EmployeeDto findEmployeeDtoByEmail(String email);
    EmployeeRequestDto findEmployeeRequestDtoByEmail(String email);

    void deleteEmployeeByEmail(EmployeeRequestDto employeeRequestDto);
}
