package net.javaguides.Employee_Management_System.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "designations")
public class Designation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long did;

    @Column(name = "designation_name")
    @NotBlank(message = "Designation name is mandatory")
    private String designationName;

    @OneToMany(mappedBy = "designation")
    @JsonIgnore
    private Set<Employee> employees = new HashSet<>();
}
