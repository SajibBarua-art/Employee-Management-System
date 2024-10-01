package net.javaguides.Employee_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.javaguides.Employee_Management_System.entity.Project;
import net.javaguides.Employee_Management_System.entity.Designation;
import net.javaguides.Employee_Management_System.entity.Role;
import net.javaguides.Employee_Management_System.entity.TodoList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


// To transport data between client and server
// To use this as a response of REST API
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private TodoList todoList;
    private Designation designation;
    private Set<Role> roles = new HashSet<>();
    private List<Project> projects = new ArrayList<>();
}