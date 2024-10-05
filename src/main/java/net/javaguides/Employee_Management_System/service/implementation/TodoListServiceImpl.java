package net.javaguides.Employee_Management_System.service.implementation;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import net.javaguides.Employee_Management_System.dto.TodoListDto;
import net.javaguides.Employee_Management_System.entity.Employee;
import net.javaguides.Employee_Management_System.entity.TodoList;
import net.javaguides.Employee_Management_System.exception.TodoListNotFoundException;
import net.javaguides.Employee_Management_System.mapper.TodoListMapper;
import net.javaguides.Employee_Management_System.repository.EmployeeRepository;
import net.javaguides.Employee_Management_System.repository.TodoListRepository;
import net.javaguides.Employee_Management_System.service.EmployeeService;
import net.javaguides.Employee_Management_System.service.MessageService;
import net.javaguides.Employee_Management_System.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoListServiceImpl implements TodoListService {
    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public TodoListDto getTodoList() {
        String email = employeeService.getUserEmail();

        EmployeeDto employeeDto = employeeService.findEmployeeDtoByEmail(email);

        return TodoListMapper.mapToTodoListDto(employeeDto.getTodoList());
    }

    @Override
    public TodoListDto getTodoList(Long todoListId) {
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new TodoListNotFoundException(
                   messageService.getMessage("todoList.notfound", todoListId)
                ));

        return TodoListMapper.mapToTodoListDto(todoList);
    }

    @Override
    public Set<TodoListDto> getAllTodoLists() {
        return todoListRepository.findAll()
                .stream().map(todoList -> TodoListMapper.mapToTodoListDto(todoList))
                .collect(Collectors.toSet());
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
    public TodoListDto updateTodoList(TodoListDto todoListDto) {
        String email = employeeService.getUserEmail();
        Long tid = employeeService.findEmployeeDtoByEmail(email).getId();

        return this.updateTodoList(tid, todoListDto);
    }

    @Override
    public void deleteTodoList(Long todoListId) {
        todoListRepository.findById(todoListId)
                .orElseThrow(() -> new TodoListNotFoundException(
                        messageService.getMessage("todoList.notfound", todoListId)
                ));

        todoListRepository.deleteById(todoListId);
    }

    @Override
    public TodoListDto updateTodoFields(TodoListDto updatedTodoListDto) {
        String email = employeeService.getUserEmail();
        Employee employee = employeeRepository.findByEmail(email);
        TodoList todoList = employee.getTodoList();

        todoList.setTodoFields(updatedTodoListDto.getTodoFields());

        TodoList updatedTodoList = todoListRepository.save(todoList);
        return TodoListMapper.mapToTodoListDto(updatedTodoList);
    }

    @Override
    public void deleteTodoList() {
        String email = employeeService.getUserEmail();

        Employee employee = employeeRepository.findByEmail(email);

        employee.setTodoList(null);
        employeeRepository.save(employee);

        Long tid = employee.getTodoList().getTid();
        this.deleteTodoList(tid);
    }
}
