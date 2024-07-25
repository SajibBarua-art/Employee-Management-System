package net.javaguides.Employee_Management_System.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity // used to specify that a class is an entity and is mapped to a database table
@Table(name="empolyees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email_id", nullable = false, unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "todo_list_id")
    @JsonManagedReference
    private TodoList todoList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Role role;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();
}
