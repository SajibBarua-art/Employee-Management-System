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
@Table(name = "designations")
public class Designation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long did;

    @Column(name = "designation_name")
    private String designationName;

    @OneToMany(mappedBy = "designation")
    @JsonIgnore
    private List<Employee> employees = new ArrayList<>();
}
