package net.javaguides.Employee_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.javaguides.Employee_Management_System.entity.Employee;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long pid;
    private String projectName;

    private List<EmployeeIdDto> employees = new ArrayList<>();
}
