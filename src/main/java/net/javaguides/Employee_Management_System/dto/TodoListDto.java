package net.javaguides.Employee_Management_System.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.javaguides.Employee_Management_System.entity.Employee;
import net.javaguides.Employee_Management_System.entity.Designation;

@Data
@NoArgsConstructor
@Getter
@Setter
public class TodoListDto {
    private Long tid;
    private String title;
    private String description;
    private int priority;

    private Employee employee;
    private Designation designation;
}
