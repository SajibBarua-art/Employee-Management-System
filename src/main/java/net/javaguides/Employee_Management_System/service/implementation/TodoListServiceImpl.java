package net.javaguides.Employee_Management_System.service.implementation;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.TodoListDto;
import net.javaguides.Employee_Management_System.entity.TodoList;
import net.javaguides.Employee_Management_System.exception.TodoListNotFoundException;
import net.javaguides.Employee_Management_System.mapper.TodoListMapper;
import net.javaguides.Employee_Management_System.repository.TodoListRepository;
import net.javaguides.Employee_Management_System.service.MessageService;
import net.javaguides.Employee_Management_System.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoListServiceImpl implements TodoListService {
    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public TodoListDto getTodoListById(long todoListId) {
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new TodoListNotFoundException(
                   messageService.getMessage("todoList.notfound", todoListId)
                ));

        return TodoListMapper.mapToTodoListDto(todoList);
    }

    @Override
    public List<TodoListDto> getAllTodoLists() {
        List<TodoList> todoLists = todoListRepository.findAll();
        return todoLists.stream().map(todoList -> TodoListMapper.mapToTodoListDto(todoList))
                .collect(Collectors.toList());
    }

    @Override
    public TodoListDto createTodoList(TodoListDto todoListDto) {
        TodoList todoList = TodoListMapper.mapToTodoList(todoListDto);

        TodoList savedTodoList = todoListRepository.save(todoList);

        return TodoListMapper.mapToTodoListDto(savedTodoList);
    }

    @Override
    public TodoListDto updateTodoList(Long tid, TodoListDto todoListDto) {
        TodoList todoList = todoListRepository.findById(tid)
                .orElseThrow(() -> new TodoListNotFoundException(
                        messageService.getMessage("todoList.notfound", tid)
                ));

        todoList.setTodoFields(todoListDto.getTodoFields());
        TodoList updatedTodoList = todoListRepository.save(todoList);

        return TodoListMapper.mapToTodoListDto(todoListRepository.save(updatedTodoList));
    }

    @Override
    public void deleteTodoList(long todoListId) {
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new TodoListNotFoundException(
                        messageService.getMessage("todoList.notfound", todoListId)
                ));

        todoListRepository.deleteById(todoListId);
    }
}
