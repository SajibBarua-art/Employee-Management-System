package net.javaguides.Employee_Management_System.mapper;


import net.javaguides.Employee_Management_System.dto.RoleDto;
import net.javaguides.Employee_Management_System.entity.Role;

public class RoleMapper {
    public static RoleDto mapToRoleDto(Role todoList) {
        return new RoleDto(
                todoList.getRid(),
                todoList.getName()
        );
    }

    public static Role mapToRole(RoleDto todoListDto) {
        return new Role(
                todoListDto.getRid(),
                todoListDto.getName()
        );
    }
}
