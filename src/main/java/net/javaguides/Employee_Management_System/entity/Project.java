package net.javaguides.Employee_Management_System.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private String projectName;

    @ManyToMany(mappedBy = "projects", cascade = {CascadeType.ALL})
    @JsonIgnore // Prevent serialization of Employees when serializing Project
    private List<Employee> employees = new ArrayList<>();
}
