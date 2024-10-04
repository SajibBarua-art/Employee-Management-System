package net.javaguides.Employee_Management_System.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
    private Set<TodoField> todoFields = new HashSet<>();

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
