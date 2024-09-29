package net.javaguides.Employee_Management_System.service.implementation;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.RoleDto;
import net.javaguides.Employee_Management_System.entity.Role;
import net.javaguides.Employee_Management_System.exception.RoleNotFoundException;
import net.javaguides.Employee_Management_System.mapper.RoleMapper;
import net.javaguides.Employee_Management_System.repository.RoleRepository;
import net.javaguides.Employee_Management_System.service.MessageService;
import net.javaguides.Employee_Management_System.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private MessageService messageService;

    @Override
    public RoleDto getRoleById(long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(
                        messageService.getMessage("role.notfound", roleId)
                ));

        return RoleMapper.mapToRoleDto(role);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(role -> RoleMapper.mapToRoleDto(role))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role = RoleMapper.mapToRole(roleDto);

        Role savedRole = roleRepository.save(role);

        return RoleMapper.mapToRoleDto(savedRole);
    }

    @Override
    public RoleDto updateRole(Long tid, RoleDto roleDto) {
        Role role = roleRepository.findById(tid)
                .orElseThrow(() -> new RoleNotFoundException(
                        messageService.getMessage("role.notfound", tid)
                ));

        role.setName(roleDto.getName());
        Role updatedRole = roleRepository.save(role);

        return RoleMapper.mapToRoleDto(roleRepository.save(updatedRole));
    }

    @Override
    public void deleteRole(long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(
                        messageService.getMessage("role.notfound", roleId)
                ));

        roleRepository.deleteById(roleId);
    }
}
