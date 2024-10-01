package net.javaguides.Employee_Management_System.controller;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import net.javaguides.Employee_Management_System.dto.TodoListDto;
import net.javaguides.Employee_Management_System.service.EmployeeService;
import net.javaguides.Employee_Management_System.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // this class is capable to handle http request
@RequestMapping("/api/admin") // to define the base url of all the rest APIs
@AllArgsConstructor
public class AdminController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TodoListService todoListService;

    // Build get employee rest api
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long employeeId) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employeeDto);
    }

    // Build get all employees REST api
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // Build update an employee REST api
    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    // assign a role to an employee
    @PostMapping("/employees/{employeeId}/roles/{roleId}")
    public ResponseEntity<EmployeeDto> assignRole(@PathVariable Long employeeId, @PathVariable Long roleId) {
        EmployeeDto updatedEmployee = employeeService.assignRoleToEmployee(employeeId, roleId);
        return ResponseEntity.ok(updatedEmployee);
    }

    // naturally seems not right but whatever assume it
    // Builds get all todoList REST api
    @GetMapping("/todoLists")
    ResponseEntity<?> getAllTodoList(){
        List<TodoListDto> todoLists = todoListService.getAllTodoLists();
        return ResponseEntity.ok(todoLists);
    }

    // Build get a todoList by ID REST api
    @GetMapping("/todoLists/{id}")
    ResponseEntity<?> getTodoListById(@PathVariable Long id){
        TodoListDto todoListDto = todoListService.getTodoList(id);
        return ResponseEntity.ok(todoListDto);
    }
}
