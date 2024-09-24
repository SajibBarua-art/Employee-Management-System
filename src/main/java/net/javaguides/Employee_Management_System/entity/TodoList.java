package net.javaguides.Employee_Management_System.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todo_lists")
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tid;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="priority")
    private int priority;

    @OneToOne(mappedBy = "todoList")
    @JsonIgnore
    private Employee employee;
}
