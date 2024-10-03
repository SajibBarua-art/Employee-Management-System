package net.javaguides.Employee_Management_System.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "todo_lists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "employee") // To avoid circular reference in toString()
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;

    @ElementCollection
    @CollectionTable(name = "todo_fields", joinColumns = @JoinColumn(name = "todo_list_id"))
    @Valid
    private List<TodoField> todoFields = new ArrayList<>();

    // Embedded class representing the fields in the TodoList
    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class TodoField {
        @Column(name = "title")
        @NotBlank(message = "Title field is mandatory")
        private String title;

        @Column(name = "description")
        private String description;

        @Column(name = "priority")
        private Integer priority;
    }
}
