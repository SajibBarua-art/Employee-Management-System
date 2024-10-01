package net.javaguides.Employee_Management_System.controller;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.TodoListDto;
import net.javaguides.Employee_Management_System.service.TodoListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // this class is capable to handle http request
@RequestMapping("/api/todoLists") // to define the base url of all the rest APIs
@AllArgsConstructor
public class TodoListController {
    private TodoListService todoListService;

    @PostMapping
    public ResponseEntity<TodoListDto> createTodoList(@RequestBody TodoListDto todoListDto){
        TodoListDto savedTodoList = todoListService.createTodoList(todoListDto);
        return new ResponseEntity<>(savedTodoList, HttpStatus.CREATED);
    }

    // we don't need any id because we are using username from authentication
    @GetMapping
    ResponseEntity<?> getTodoList(){
        TodoListDto todoListDto = todoListService.getTodoList();
        return ResponseEntity.ok(todoListDto);
    }

    @PutMapping
    ResponseEntity<?> updateTodoList(@RequestBody TodoListDto updatedTodoListDto){
        TodoListDto todoListDto = todoListService.updateTodoList(updatedTodoListDto);
        return ResponseEntity.ok(todoListDto);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteTodoList(){
        todoListService.deleteTodoList();
        return ResponseEntity.ok("Your TodoList deleted successfully");
    }
}
