package net.javaguides.Employee_Management_System.repository;

import net.javaguides.Employee_Management_System.entity.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {
}
