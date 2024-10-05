package net.javaguides.Employee_Management_System.service.implementation;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import net.javaguides.Employee_Management_System.dto.RoleDto;
import net.javaguides.Employee_Management_System.entity.Employee;
import net.javaguides.Employee_Management_System.entity.Role;
import net.javaguides.Employee_Management_System.exception.RequestBodyFormatNotMatchedException;
import net.javaguides.Employee_Management_System.exception.RoleAlreadyExistsException;
import net.javaguides.Employee_Management_System.exception.RoleNotFoundException;
import net.javaguides.Employee_Management_System.mapper.EmployeeMapper;
import net.javaguides.Employee_Management_System.mapper.RoleMapper;
import net.javaguides.Employee_Management_System.repository.EmployeeRepository;
import net.javaguides.Employee_Management_System.repository.RoleRepository;
import net.javaguides.Employee_Management_System.service.MessageService;
import net.javaguides.Employee_Management_System.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
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
    public RoleDto getRoleById(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(
                        messageService.getMessage("role.notfound", roleId)
                ));

        return RoleMapper.mapToRoleDto(role);
    }

    @Override
    public Set<RoleDto> getAllRoles() {
        return roleRepository.findAll()
                .stream().map(role -> RoleMapper.mapToRoleDto(role))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleDto createRole(Role role) {
        if(role == null || role.getName() == null) {
            // to handle null name
            throw new RequestBodyFormatNotMatchedException(messageService.getMessage(
                    "format.not.matched", "name"
            ));
        }

        Optional<Role> responseRole = roleRepository.findByName(role.getName());
        if(responseRole.isPresent()) {
            throw new RoleAlreadyExistsException(
                    messageService.getMessage("role.exists", role.getName())
            );
        }
        Role savedRole = roleRepository.save(role);

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
    public void deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RoleNotFoundException(
                            messageService.getMessage("role.notfound", roleId)
                    ));

        role.removeAllEmployees();

        roleRepository.delete(role);
    }
}
