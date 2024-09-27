package net.javaguides.Employee_Management_System.controller;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.TodoListDto;
import net.javaguides.Employee_Management_System.service.TodoListService;
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

    @GetMapping("{id}")
    ResponseEntity<?> getTodoListById(@PathVariable long id){
        TodoListDto todoListDto = todoListService.getTodoListById(id);
        return ResponseEntity.ok(todoListDto);
    }

    @GetMapping
    ResponseEntity<?> getAllTodoList(){
        List<TodoListDto> todoLists = todoListService.getAllTodoLists();
        return ResponseEntity.ok(todoLists);
    }

    @PutMapping("{id}")
    ResponseEntity<?> updateTodoList(@PathVariable long id, @RequestBody TodoListDto updatedTodoListDto){
        TodoListDto todoListDto = todoListService.updateTodoList(id, updatedTodoListDto);
        return ResponseEntity.ok(todoListDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTodoList(@PathVariable long id){
        todoListService.deleteTodoList(id);
        return ResponseEntity.ok("TodoList deleted successfully");
    }
}
