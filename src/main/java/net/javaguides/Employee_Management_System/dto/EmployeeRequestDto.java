package net.javaguides.Employee_Management_System.dto;

import lombok.*;
import net.javaguides.Employee_Management_System.entity.Designation;
import net.javaguides.Employee_Management_System.entity.Project;
import net.javaguides.Employee_Management_System.entity.Role;
import net.javaguides.Employee_Management_System.entity.TodoList;

import java.util.HashSet;
import java.util.HashSet;
import java.util.Set;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private String password;

    private TodoList todoList;
    private Designation designation;
    private Set<Role> roles = new HashSet<>();
    private Set<Project> projects = new HashSet<>();
}

