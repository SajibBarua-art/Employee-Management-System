package net.javaguides.Employee_Management_System.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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

    public Role(String name) {
        this.name = name;
    }
}
