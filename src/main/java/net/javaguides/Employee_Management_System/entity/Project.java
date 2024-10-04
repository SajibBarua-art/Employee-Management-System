package net.javaguides.Employee_Management_System.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.HashSet;
import java.util.HashSet;
import java.util.Set;
import java.util.Set;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Column(name = "project_name")
    @NotBlank(message = "Project name is mandatory")
    private String projectName;

    @ManyToMany(mappedBy = "projects", cascade = {CascadeType.ALL})
    @JsonIgnore
    private Set<Employee> employees = new HashSet<>();
}
