package net.javaguides.Employee_Management_System.mapper;

import net.javaguides.Employee_Management_System.dto.TodoListDto;
import net.javaguides.Employee_Management_System.entity.TodoList;

public class TodoListMapper {
    public static TodoListDto mapToTodoListDto(TodoList todoList) {
        return new TodoListDto(
                todoList.getTid(),
                todoList.getTodoFields()
        );
    }

    public static TodoList mapToTodoList(TodoListDto todoListDto) {
        return new TodoList(
                todoListDto.getTid(),
                todoListDto.getTodoFields()
        );
    }
}
