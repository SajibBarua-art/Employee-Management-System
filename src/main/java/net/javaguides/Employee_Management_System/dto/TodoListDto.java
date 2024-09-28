package net.javaguides.Employee_Management_System.dto;

import lombok.*;
import net.javaguides.Employee_Management_System.entity.TodoList;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class TodoListDto {
    private Long tid;

    // List of TodoFieldDto to represent the fields inside TodoList
    private List<TodoList.TodoField> todoFields;
}
