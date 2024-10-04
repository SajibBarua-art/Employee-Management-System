package net.javaguides.Employee_Management_System.service;

import net.javaguides.Employee_Management_System.dto.TodoListDto;

import java.util.Set;

public interface TodoListService {
    TodoListDto getTodoList();
    TodoListDto getTodoList(Long id);

    Set<TodoListDto> getAllTodoLists();

    TodoListDto createTodoList(TodoListDto todoListDto);

    TodoListDto updateTodoList(Long pid, TodoListDto todoListDto);
    TodoListDto updateTodoList(TodoListDto todoListDto);

    void deleteTodoList();
    void deleteTodoList(Long todoListId);
}
