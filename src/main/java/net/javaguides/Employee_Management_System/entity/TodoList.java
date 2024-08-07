package net.javaguides.Employee_Management_System.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tid;

    private String title;
    private String description;
    private int priority;

    @OneToOne(mappedBy = "todoList")
    @JsonBackReference
    private Employee employee;
}
