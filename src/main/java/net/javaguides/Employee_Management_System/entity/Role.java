package net.javaguides.Employee_Management_System.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;

    @Column(name = "name", unique = true, nullable = false)
    @NotBlank(message = "Name field is mandatory")
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<Employee> employees = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getRoles().remove(this);
    }

    // Method to remove all employee associations
    public void removeAllEmployees() {
        for (Employee employee : new HashSet<>(employees)) {
            employee.removeRole(this);
        }
    }
}
