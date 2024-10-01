package net.javaguides.Employee_Management_System.service.implementation;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import net.javaguides.Employee_Management_System.dto.EmployeeRequestDto;
import net.javaguides.Employee_Management_System.dto.RoleDto;
import net.javaguides.Employee_Management_System.entity.*;
import net.javaguides.Employee_Management_System.exception.*;
import net.javaguides.Employee_Management_System.mapper.EmployeeMapper;
import net.javaguides.Employee_Management_System.mapper.RoleMapper;
import net.javaguides.Employee_Management_System.repository.*;
import net.javaguides.Employee_Management_System.service.EmployeeService;
import net.javaguides.Employee_Management_System.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Override
    public EmployeeDto createEmployee(EmployeeRequestDto employeeRequestDto) {
        if(employeeRepository.existsByEmail(employeeRequestDto.getEmail())) {
            logger.error("Email Already Exists {}", employeeRequestDto.getEmail());
            throw new EmailAlreadyExistsException(
                    messageService.getMessage("employee.email.exists", employeeRequestDto.getEmail())
            );
        }
        // to convert EmployeeDta into employee
        // because we need to store employee into the database
        Employee employee = EmployeeMapper.mapToEmployee(employeeRequestDto);

        // Encoding password
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        // Handle TodoList relationship
        if (employee.getTodoList() != null) {
            TodoList todoList = employee.getTodoList();
            if (todoList.getTid() != null) {
                // Fetch the existing TodoList from the database by its ID
                TodoList existingTodoList = todoListRepository.findById(todoList.getTid())
                        .orElseThrow(() -> new TodoListNotFoundException(
                                messageService.getMessage("todoList.notfound", todoList.getTid())
                        ));

                // Associate the existing TodoList with the employee
                employee.setTodoList(existingTodoList);
            }
        }

        // Handle Designation relationship
        if (employee.getDesignation() != null) {
            Designation designation = employee.getDesignation();
            if (designation.getDid() != null) {
                // Fetch the existing Designation from the database by its ID
                Designation existingDesignation = designationRepository.findById(designation.getDid())
                        .orElseThrow(() -> new DesignationNotFoundException(
                                messageService.getMessage("designation.notfound", designation.getDid())
                        ));

                // Associate the existing Designation with the employee
                employee.setDesignation(existingDesignation);

                // Add the employee to the Designation's employee list
                if (existingDesignation.getEmployees() == null) {
                    existingDesignation.setEmployees(new ArrayList<>());
                }
                existingDesignation.getEmployees().add(employee);
            } else {
                // For a new Designation, add the employee
                if (designation.getEmployees() == null) {
                    designation.setEmployees(new ArrayList<>());
                }
                designation.getEmployees().add(employee);
            }
        }

        // Handle Role relationship
        // Check if the "USER" role exists
        Role userRole = roleRepository.findByName("USER")
            .orElseGet(() -> {
                // If the "USER" role doesn't exist, create and save it
                Role newUserRole = new Role();
                newUserRole.setName("USER");
                return roleRepository.save(newUserRole);
            });
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        if(employee.getRoles() != null) {
            for(Role role: employee.getRoles()) {
                if(role.getRid() != null) {
                    Role existingRole = roleRepository.findById(role.getRid())
                            .orElseThrow(() -> new RoleNotFoundException(
                                    messageService.getMessage("role.notfound", role.getRid())
                            ));

                    roles.add(existingRole);
                }
            }
        }
        employee.setRoles(roles);

        // Handle Projects relationship
        if (employee.getProjects() != null) {
            List<Project> projects = new ArrayList<>();
            for (Project project : employee.getProjects()) {
                if (project.getPid() != null) {
                    // Fetch the existing project from the database by its ID
                    Project existingProject = projectRepository.findById(project.getPid())
                            .orElseThrow(() -> new ProjectNotFoundException(
                                    messageService.getMessage("project.notFound", project.getPid())
                            ));

                    // Associate the existing project with the employee
                    projects.add(existingProject);

                    // Update the employees list of the project to include this employee
                    if (existingProject.getEmployees() == null) {
                        existingProject.setEmployees(new ArrayList<>());
                    }
                    existingProject.getEmployees().add(employee);
                } else {
                    // If project doesn't have an ID, it's a new project, so add it to the employee
                    if (project.getEmployees() == null) {
                        project.setEmployees(new ArrayList<>());
                    }
                    project.getEmployees().add(employee);
                    projects.add(project);
                }
            }
            employee.setProjects(projects); // Set the updated list of projects to the employee
        }
        // we don't have to store todoList entity at database here
        // because we used cascadeType.ALL in the entity

        // save into employee jpa entity to database
        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

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
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees =  employeeRepository.findAll();
        return employees.stream().map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeRequestDto updatedEmployeeRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        EmployeeRequestDto employeeRequestDto = this.findEmployeeRequestDtoByEmail(email);

        // Handle password
        if(updatedEmployeeRequestDto.getPassword() != null) {
            employeeRequestDto.setPassword(updatedEmployeeRequestDto.getPassword());
        }

        // Handle Roles
        if(updatedEmployeeRequestDto.getRoles() != null) {
            Set<Role> roles = new HashSet<>();
            for(Role role: updatedEmployeeRequestDto.getRoles()) {
                if(role.getRid() != null) {
                    Role existingRole = roleRepository.findById(role.getRid())
                            .orElseThrow(() -> new RoleNotFoundException(
                                    messageService.getMessage("role.notfound", role.getRid())
                            ));
                    roles.add(existingRole);
                }
            }
            employeeRequestDto.setRoles(roles);
        }

        // Handle TodoList relationship
        if (updatedEmployeeRequestDto.getTodoList() != null && updatedEmployeeRequestDto.getTodoList().getTodoFields() != null) {
            TodoList todoList = employeeRequestDto.getTodoList();
            todoList.setTodoFields(updatedEmployeeRequestDto.getTodoList().getTodoFields());
        }

        // Handle designation relationship
        if (employeeRequestDto.getDesignation() != null) {
            Designation designation = employeeRequestDto.getDesignation();
            if (designation.getEmployees() == null) {
                designation.setEmployees(new ArrayList<>());
            }
            designation.getEmployees().add(EmployeeMapper.mapToEmployee(employeeRequestDto));
        }

        // Update Projects
        List<Project> projects = updatedEmployeeRequestDto.getProjects().stream()
                .map(projectDto -> {
                    Project project = projectRepository.findById(projectDto.getPid())
                            .orElse(new Project(projectDto.getPid(), projectDto.getProjectName(), new ArrayList<>()));
                    return project;
                })
                .collect(Collectors.toList());
        employeeRequestDto.setProjects(projects);

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
    public void deleteEmployeeByEmail(EmployeeRequestDto responseEmployeeRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        EmployeeRequestDto employeeRequestDto = this.findEmployeeRequestDtoByEmail(email);

        if(employeeRequestDto.getPassword() != responseEmployeeRequestDto.getPassword()) {
            throw new PasswordNotMatchedException(
                    messageService.getMessage("password.not.matched")
            );
        }

        employeeRepository.deleteById(employeeRequestDto.getId());
    }
}
