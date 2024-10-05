package net.javaguides.Employee_Management_System.mapper;


import net.javaguides.Employee_Management_System.dto.EmployeeIdDto;
import net.javaguides.Employee_Management_System.dto.RoleDto;
import net.javaguides.Employee_Management_System.entity.Role;

import java.util.stream.Collectors;

public class RoleMapper {
    public static RoleDto mapToRoleDto(Role todoList) {
        return new RoleDto(
                todoList.getRid(),
                todoList.getName(),
                todoList.getEmployees().stream()
                        .map(employee -> new EmployeeIdDto(employee.getId()))
                        .collect(Collectors.toSet())
        );
    }

//    public static Role mapToRole(RoleDto todoListDto) {
//        return new Role(
//                todoListDto.getRid(),
//                todoListDto.getName(),
//                todoListDto.getEmployees()
//        );
//    }
}
