package net.javaguides.Employee_Management_System.service.implementation;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.RoleDto;
import net.javaguides.Employee_Management_System.entity.Employee;
import net.javaguides.Employee_Management_System.entity.Role;
import net.javaguides.Employee_Management_System.exception.RequestBodyFormatNotMatchedException;
import net.javaguides.Employee_Management_System.exception.RoleAlreadyExistsException;
import net.javaguides.Employee_Management_System.exception.RoleNotFoundException;
import net.javaguides.Employee_Management_System.mapper.RoleMapper;
import net.javaguides.Employee_Management_System.repository.EmployeeRepository;
import net.javaguides.Employee_Management_System.repository.RoleRepository;
import net.javaguides.Employee_Management_System.service.MessageService;
import net.javaguides.Employee_Management_System.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private EmployeeRepository employeeRepository;

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
        if(roleDto == null || roleDto.getName() == null) {
            // to handle null name
            throw new RequestBodyFormatNotMatchedException(messageService.getMessage(
                    "format.not.matched", "name"
            ));
        }

        Optional<Role> role = roleRepository.findByName(roleDto.getName());
        if(role.isPresent()) {
            throw new RoleAlreadyExistsException(
                    messageService.getMessage("role.exists", roleDto.getName())
            );
        }
        Role savedRole = roleRepository.save(RoleMapper.mapToRole(roleDto));

        return RoleMapper.mapToRoleDto(savedRole);
    }

    @Override
    public RoleDto updateRole(Long rid, RoleDto roleDto) {
        if(roleDto == null || roleDto.getName() == null) {
            throw new RequestBodyFormatNotMatchedException(messageService.getMessage(
                    "format.not.matched", "name"
            ));
        }

        Role role = roleRepository.findById(rid)
                .orElseThrow(() -> new RoleNotFoundException(
                        messageService.getMessage("role.notfound", rid)
                ));

        role.setName(roleDto.getName());
        Role updatedRole = roleRepository.save(role);

        return RoleMapper.mapToRoleDto(roleRepository.save(updatedRole));
    }

    @Override
    @Transactional
    public void deleteRole(long roleId) {
        Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RoleNotFoundException(
                            messageService.getMessage("role.notfound", roleId)
                    ));

        // Remove the role from all employees who have it
        List<Employee> employees = employeeRepository.findAllByRolesContaining(role);
        for (Employee employee : employees) {
            employee.getRoles().remove(role);
            employeeRepository.save(employee);  // Save to update the join table
        }

        roleRepository.deleteById(roleId);
    }
}
