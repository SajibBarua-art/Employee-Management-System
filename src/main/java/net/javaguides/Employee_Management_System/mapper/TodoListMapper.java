package net.javaguides.Employee_Management_System.mapper;

import net.javaguides.Employee_Management_System.dto.TodoListDto;
import net.javaguides.Employee_Management_System.entity.TodoList;

public class TodoListMapper {
    public static TodoListDto mapToTodoListDto(TodoList todoList) {
        TodoListDto dto = new TodoListDto();
        dto.setTid(todoList.getTid());
        dto.setTitle(todoList.getTitle());
        dto.setDescription(todoList.getDescription());
        dto.setPriority(todoList.getPriority());
        return dto;
    }

    public static TodoList mapToTodoList(TodoListDto dto) {
        TodoList todoList = new TodoList();
        todoList.setTid(dto.getTid());
        todoList.setTitle(dto.getTitle());
        todoList.setDescription(dto.getDescription());
        todoList.setPriority(dto.getPriority());
        return todoList;
    }
}
