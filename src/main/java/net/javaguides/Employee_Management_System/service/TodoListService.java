package net.javaguides.Employee_Management_System.service;

import net.javaguides.Employee_Management_System.dto.TodoListDto;

import java.util.List;

public interface TodoListService {
    TodoListDto getTodoListById(long id);

    List<TodoListDto> getAllTodoLists();

    TodoListDto createTodoList(TodoListDto todoListDto);

    TodoListDto updateTodoList(Long pid, TodoListDto todoListDto);

    void deleteTodoList(long todoListId);
}
