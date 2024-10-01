package net.javaguides.Employee_Management_System.service;

import net.javaguides.Employee_Management_System.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto getRoleById(Long id);

    List<RoleDto> getAllRoles();

    RoleDto createRole(RoleDto roleDto);

    RoleDto updateRole(Long rid, RoleDto roleDto);

    void deleteRole(Long roleId);
}
