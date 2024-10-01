package net.javaguides.Employee_Management_System.dto;

import lombok.*;
import net.javaguides.Employee_Management_System.entity.Designation;
import net.javaguides.Employee_Management_System.entity.Project;
import net.javaguides.Employee_Management_System.entity.Role;
import net.javaguides.Employee_Management_System.entity.TodoList;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @NotBlank
    private String password;

    private TodoList todoList;
    private Designation designation;
    private Set<Role> roles = new HashSet<>();
    private List<Project> projects = new ArrayList<>();
}

