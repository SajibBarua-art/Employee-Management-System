package net.javaguides.Employee_Management_System.mapper;

import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import net.javaguides.Employee_Management_System.dto.RoleDto;
import net.javaguides.Employee_Management_System.entity.Employee;
import net.javaguides.Employee_Management_System.entity.Role;

public class RoleMapper {
    public static RoleDto mapToRoleDto(Role role) {
        return new RoleDto(
                role.getRid(),
                role.getRoleName(),
                role.getEmployees()
        );
    }

    public static Role mapToRole(RoleDto roleDto) {
        return new Role(
                roleDto.getRid(),
                roleDto.getRoleName(),
                roleDto.getEmployees()
        );
    }
}
