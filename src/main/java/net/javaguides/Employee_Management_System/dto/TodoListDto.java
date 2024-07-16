package net.javaguides.Employee_Management_System.dto;

import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaguides.Employee_Management_System.entity.Employee;

@Data
@NoArgsConstructor
public class TodoListDto {
    private long tid;
    private String title;
    private String description;
    private int priority;
    private Employee employee;
}
