package net.javaguides.Employee_Management_System.controller;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import net.javaguides.Employee_Management_System.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // this class is capable to handle http request
@RequestMapping("/api/employees") // to define the base url of all the rest APIs
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;

    // Build post employee REST api
    @PostMapping // to map incoming http post request to this method
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        // @RequestBody: Extract the json from the http request and
        // convert that json into EmployeeDto java object
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // Build get employee rest api
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long employeeId) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employeeDto);
    }

    // Build get all employees REST api
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // Build update REST api
    @PutMapping("{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") long employeeId,
                                                      @RequestBody EmployeeDto updatedEmployeeDto) {
        EmployeeDto employeeDto = employeeService.updateEmployee(employeeId, updatedEmployeeDto);
        return ResponseEntity.ok(employeeDto);
    }

    // Build delete employee rest api
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
