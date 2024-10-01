package net.javaguides.Employee_Management_System.controller;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import net.javaguides.Employee_Management_System.dto.EmployeeRequestDto;
import net.javaguides.Employee_Management_System.entity.Employee;
import net.javaguides.Employee_Management_System.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // this class is capable to handle http request
@RequestMapping("/api/employees") // to define the base url of all the rest APIs
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;

    // Build get employee rest api
    @GetMapping
    public ResponseEntity<EmployeeDto> getEmployee() {
        EmployeeDto employeeDto = employeeService.getEmployee();
        return ResponseEntity.ok(employeeDto);
    }

    // Build update REST api
    @PutMapping
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeRequestDto updatedEmployeeRequestDto) {
        EmployeeDto employeeDto = employeeService.updateEmployee(updatedEmployeeRequestDto);
        return ResponseEntity.ok(employeeDto);
    }

    // Build delete employee rest api
    @DeleteMapping
    public ResponseEntity<String> deleteEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
        employeeService.deleteEmployeeByEmail(employeeRequestDto);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @PostMapping("/{employeeId}/roles/{roleId}")
    public ResponseEntity<EmployeeDto> assignRole(@PathVariable Long employeeId, @PathVariable Long roleId) {
        EmployeeDto updatedEmployee = employeeService.assignRoleToEmployee(employeeId, roleId);
        return ResponseEntity.ok(updatedEmployee);
    }
}
