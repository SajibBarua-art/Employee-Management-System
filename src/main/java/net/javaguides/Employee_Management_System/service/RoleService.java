package net.javaguides.Employee_Management_System.service;

import net.javaguides.Employee_Management_System.dto.RoleDto;
import net.javaguides.Employee_Management_System.entity.Role;

import java.util.Set;

public interface RoleService {
    RoleDto getRoleById(Long id);

    Set<RoleDto> getAllRoles();

    RoleDto createRole(Role role);

    RoleDto updateRole(Long rid, RoleDto roleDto);

    void deleteRole(Long roleId);
}
