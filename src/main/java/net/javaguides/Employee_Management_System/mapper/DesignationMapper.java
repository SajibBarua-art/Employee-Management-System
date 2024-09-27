package net.javaguides.Employee_Management_System.mapper;

import net.javaguides.Employee_Management_System.dto.DesignationDto;
import net.javaguides.Employee_Management_System.entity.Designation;

public class DesignationMapper {
    public static DesignationDto mapToDesignationDto(Designation designation) {
        return new DesignationDto(
                designation.getDid(),
                designation.getDesignationName(),
                designation.getEmployees()
        );
    }

    public static Designation mapToDesignation(DesignationDto designationDto) {
        return new Designation(
                designationDto.getDid(),
                designationDto.getDesignationName(),
                designationDto.getEmployees()
        );
    }
}
