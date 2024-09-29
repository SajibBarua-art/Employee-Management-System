package net.javaguides.Employee_Management_System.mapper;

import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import net.javaguides.Employee_Management_System.dto.EmployeeRequestDto;
import net.javaguides.Employee_Management_System.entity.Employee;

public class EmployeeMapper {
    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        return new EmployeeDto(
                 employee.getId(),
                 employee.getFirstName(),
                 employee.getLastName(),
                 employee.getEmail(),
                employee.getTodoList(),
                employee.getDesignation(),
                employee.getRoles(),
                employee.getProjects()
        );
    }

    public static Employee mapToEmployee(EmployeeRequestDto employeeRequestDto) {
        return new Employee(
                employeeRequestDto.getId(),
                employeeRequestDto.getFirstName(),
                employeeRequestDto.getLastName(),
                employeeRequestDto.getEmail(),
                employeeRequestDto.getPassword(),
                employeeRequestDto.getTodoList(),
                employeeRequestDto.getDesignation(),
                employeeRequestDto.getRoles(),
                employeeRequestDto.getProjects()
        );
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail(),
                employeeDto.getTodoList(),
                employeeDto.getDesignation(),
                employeeDto.getRoles(),
                employeeDto.getProjects()
        );
    }

    public static EmployeeRequestDto mapToEmployeeRequestDto(Employee employee) {
        return new EmployeeRequestDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPassword(),
                employee.getTodoList(),
                employee.getDesignation(),
                employee.getRoles(),
                employee.getProjects()
        );
    }
}
