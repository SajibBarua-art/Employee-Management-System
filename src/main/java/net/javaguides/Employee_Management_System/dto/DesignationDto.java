package net.javaguides.Employee_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaguides.Employee_Management_System.entity.Employee;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesignationDto {
    private Long did;
    private String designationName;

    private List<EmployeeIdDto> employees;
}
