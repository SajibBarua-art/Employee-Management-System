package net.javaguides.Employee_Management_System.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.javaguides.Employee_Management_System.entity.Employee;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private Long rid;

    private String name;

    private Set<EmployeeIdDto> employees;
}
