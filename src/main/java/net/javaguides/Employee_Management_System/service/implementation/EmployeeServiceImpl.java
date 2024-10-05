package net.javaguides.Employee_Management_System.service.implementation;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import net.javaguides.Employee_Management_System.dto.EmployeeRequestDto;
import net.javaguides.Employee_Management_System.entity.*;
import net.javaguides.Employee_Management_System.exception.*;
import net.javaguides.Employee_Management_System.mapper.EmployeeMapper;
import net.javaguides.Employee_Management_System.repository.*;
import net.javaguides.Employee_Management_System.service.EmployeeService;
import net.javaguides.Employee_Management_System.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service // to create spring bean for this class
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    // inject the dependency
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private TodoList handleTodoList(TodoList todoList) {
        if (todoList != null && todoList.getTid() != null) {
            return todoListRepository.findById(todoList.getTid())
                    .orElseThrow(() -> new TodoListNotFoundException(
                            messageService.getMessage("todoList.notfound", todoList.getTid())));
        }
        return null;
    }

    private Designation handleDesignation(Designation designation, Employee employee) {
        if (designation != null && designation.getDid() != null) {
            Designation existingDesignation = designationRepository.findById(designation.getDid())
                    .orElseThrow(() -> new DesignationNotFoundException(
                            messageService.getMessage("designation.notfound", designation.getDid())));

            // Add the employee to the Designation's employee set
            if (existingDesignation.getEmployees() == null) {
                existingDesignation.setEmployees(new HashSet<>());
            }
            existingDesignation.getEmployees().add(employee);
            return existingDesignation;
        }
        return null;
    }

    private Set<Role> handleVerifyAllRoles(Set<Role> roles) {
        Set<Role> resultRoles = new HashSet<>();
        if (roles != null) {
            for (Role role : roles) {
                if (role.getRid() != null) {
                    Role existingRole = roleRepository.findById(role.getRid())
                            .orElseThrow(() -> new RoleNotFoundException(
                                    messageService.getMessage("role.notfound", role.getRid())));
                    resultRoles.add(existingRole);
                }
            }
        }
        return resultRoles;
    }
    
    private Set<Role> handleRoles(Set<Role> roles) {
        // Check if the "USER" role exists, or create it
        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(new Role("USER")));

        Set<Role> resultRoles = handleVerifyAllRoles(roles);
        resultRoles.add(userRole);

        return resultRoles;
    }

    private Set<Project> handleProjects(Set<Project> projects, Employee employee) {
        Set<Project> resultProjects = new HashSet<>();

        if (projects != null) {
            for (Project project : projects) {
                if (project.getPid() != null) {
                    Project existingProject = projectRepository.findById(project.getPid())
                            .orElseThrow(() -> new ProjectNotFoundException(
                                    messageService.getMessage("project.notFound", project.getPid())));

                    // Add the employee to the project
                    if (existingProject.getEmployees() == null) {
                        existingProject.setEmployees(new HashSet<>());
                    }
                    existingProject.getEmployees().add(employee);
                    resultProjects.add(existingProject);
                }
            }
        }
        return resultProjects;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeRequestDto employeeRequestDto) {
        if (employeeRepository.existsByEmail(employeeRequestDto.getEmail())) {
            logger.error("Email Already Exists {}", employeeRequestDto.getEmail());
            throw new EmailAlreadyExistsException(
                    messageService.getMessage("employee.email.exists", employeeRequestDto.getEmail()));
        }

        // Convert EmployeeRequestDto to Employee entity
        Employee employee = EmployeeMapper.mapToEmployee(employeeRequestDto);

        // Encode password
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        // Handle TodoList, Designation, Roles, Projects
        employee.setTodoList(handleTodoList(employee.getTodoList()));
        employee.setDesignation(handleDesignation(employee.getDesignation(), employee));
        employee.setRoles(handleRoles(employee.getRoles()));
        employee.setProjects(handleProjects(employee.getProjects(), employee));

        // Save the employee entity to the database
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployee() {
        String email = this.getUserEmail();

        EmployeeDto employeeDto = this.findEmployeeDtoByEmail(email);

        return employeeDto;
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        messageService.getMessage("employee.notfound", employeeId)
                ));

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public EmployeeDto assignRoleToEmployee(Long employeeId, Long roleId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        messageService.getMessage("employee.notfound", employeeId)
                ));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        employee.getRoles().add(role);
        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public Set<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)  // Map Employee to EmployeeDto
                .collect(Collectors.toSet());           // Collect as a Set
    }

    private Set<Project> handleProjectsUpdate(Set<Project> projects) {
        if(projects == null) return null;

        return projects.stream()
                .map(project -> projectRepository.findById(project.getPid())
                        .orElseThrow(() -> new ProjectNotFoundException(
                                messageService.getMessage("project.notfound", project.getPid())))
                )
                .collect(Collectors.toSet());
    }

    private Designation handleDesignationUpdate(Designation updatedDesignation) {
        if(updatedDesignation == null) return null;

        return designationRepository.findById(updatedDesignation.getDid())
                .orElseThrow(() -> new DesignationNotFoundException(
                        messageService.getMessage("designation.notfound", updatedDesignation.getDid())
                ));
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployeeDto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        messageService.getMessage("employee.notfound", employeeId)));

        // Update Roles
        employee.setRoles(handleVerifyAllRoles(updatedEmployeeDto.getRoles()));

        // Update Designation
        employee.setDesignation(handleDesignationUpdate(updatedEmployeeDto.getDesignation()));

        // Update Projects
        employee.setProjects(handleProjectsUpdate(updatedEmployeeDto.getProjects()));

        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeRequestDto updatedEmployeeRequestDto) {
        String email = this.getUserEmail();
        EmployeeRequestDto employeeRequestDto = this.findEmployeeRequestDtoByEmail(email);

        // Handle password
        if (updatedEmployeeRequestDto.getPassword() != null) {
            employeeRequestDto.setPassword(updatedEmployeeRequestDto.getPassword());
        }

        // Handle TodoList relationship (if needed)
        if (updatedEmployeeRequestDto.getTodoList() != null) {
            Long tid = updatedEmployeeRequestDto.getTodoList().getTid();
            TodoList todoList = todoListRepository.findById(tid)
                    .orElseThrow(() -> new TodoListNotFoundException(
                            messageService.getMessage("todoList.notfound", tid)
                    ));

            employeeRequestDto.setTodoList(todoList);
        }

        Employee savedEmployee = employeeRepository.save(EmployeeMapper.mapToEmployee(employeeRequestDto));
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto findEmployeeDtoByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email);
        if(employee == null) {
            throw new EmployeeNotFoundException(messageService.getMessage("employee.notfound.email", email));
        }
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public EmployeeRequestDto findEmployeeRequestDtoByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email);
        if(employee == null) {
            throw new EmployeeNotFoundException(messageService.getMessage("employee.notfound.email", email));
        }
        return EmployeeMapper.mapToEmployeeRequestDto(employee);
    }

    @Override
    @Transactional
    public void deleteEmployeeByEmail(EmployeeRequestDto responseEmployeeRequestDto) {
        String email = this.getUserEmail();

        Employee employee = employeeRepository.findByEmail(email);

        String rawPassword = responseEmployeeRequestDto.getPassword();

        // validate the password match

        if (!passwordEncoder.matches(rawPassword, employee.getPassword())) {
            throw new PasswordNotMatchedException(
                    messageService.getMessage("password.not.matched")
            );
        }

        employee.removeAllProjects();

        employee.removeAllRoles();

        employeeRepository.delete(employee);
    }

    @Override
    public String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return email;
    }
}
