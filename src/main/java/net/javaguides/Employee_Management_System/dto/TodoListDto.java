package net.javaguides.Employee_Management_System.dto;

import lombok.*;
import net.javaguides.Employee_Management_System.entity.TodoList;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class TodoListDto {
    private Long tid;

    // Set of TodoFieldDto to represent the fields inside TodoList
    private Set<TodoList.TodoField> todoFields;
}
