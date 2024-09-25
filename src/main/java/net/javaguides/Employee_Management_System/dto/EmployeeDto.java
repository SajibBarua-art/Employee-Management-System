package net.javaguides.Employee_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.javaguides.Employee_Management_System.entity.Project;
import net.javaguides.Employee_Management_System.entity.Designation;
import net.javaguides.Employee_Management_System.entity.TodoList;

import java.util.ArrayList;
import java.util.List;


// To transport data between client and server
// To use this as a response of REST API
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    private TodoList todoList;
    private Designation designation;
    private List<Project> projects = new ArrayList<>();
}