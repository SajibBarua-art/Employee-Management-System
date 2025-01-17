package net.javaguides.Employee_Management_System.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity // used to specify that a class is an entity and is mapped to a database table
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @Column(name="last_name")
    @NotBlank(message = "Last Name is mandatory")
    private String lastName;

    @Column(name="email_id", nullable = false, unique = true)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid Email Format")
    private String email;

    @NotBlank(message="Password is mandatory")
    private String password;

    // unidirectional
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "todo_list_id", referencedColumnName = "tid")
    private TodoList todoList;

    // Bidirectional
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "designation_id")
    private Designation designation;

    // Bidirectional
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "employee_roles",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    // Bidirectional
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "employee_projects",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects = new HashSet<>();

    public Employee(Long id, String firstName, String lastName, String email, TodoList todoList, Designation designation, Set<Role> roles, Set<Project> projects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.todoList = todoList;
        this.designation = designation;
        this.roles = roles;
        this.projects = projects;
    }

    // Method to remove a project
    public void removeProject(Project project) {
        this.projects.remove(project);
        project.getEmployees().remove(this);
    }

    // Method to remove all project associations
    public void removeAllProjects() {
        for (Project project : new HashSet<>(projects)) {
            project.removeEmployee(this);
        }
    }

    // Method to remove a role
    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getEmployees().remove(this);
    }

    // Method to remove all role associations
    public void removeAllRoles() {
        for (Role role : new HashSet<>(roles)) {
            role.removeEmployee(this);
        }
    }
}
