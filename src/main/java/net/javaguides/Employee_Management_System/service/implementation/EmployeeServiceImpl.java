package net.javaguides.Employee_Management_System.service.implementation;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import net.javaguides.Employee_Management_System.entity.Employee;
import net.javaguides.Employee_Management_System.entity.Project;
import net.javaguides.Employee_Management_System.entity.Designation;
import net.javaguides.Employee_Management_System.entity.TodoList;
import net.javaguides.Employee_Management_System.exception.*;
import net.javaguides.Employee_Management_System.mapper.EmployeeMapper;
import net.javaguides.Employee_Management_System.repository.EmployeeRepository;
import net.javaguides.Employee_Management_System.repository.ProjectRepository;
import net.javaguides.Employee_Management_System.repository.DesignationRepository;
import net.javaguides.Employee_Management_System.repository.TodoListRepository;
import net.javaguides.Employee_Management_System.service.EmployeeService;
import net.javaguides.Employee_Management_System.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        if(employeeRepository.existsByEmail(employeeDto.getEmail())) {
            logger.error("Email Already Exists {}", employeeDto.getEmail());
            throw new EmailAlreadyExistsException(
                    messageService.getMessage("employee.email.exists", employeeDto.getEmail())
            );
        }
        // to convert EmployeeDta into employee
        // because we need to store employee into the database
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

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

                // Set the employee in the TodoList entity
                existingTodoList.setEmployee(employee);
            } else {
                // For a new TodoList, set the employee
                todoList.setEmployee(employee);
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
    public EmployeeDto getEmployeeById(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        messageService.getMessage("employee.notfound", employeeId)
                ));

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees =  employeeRepository.findAll();
        return employees.stream().map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(long employeeId, EmployeeDto updatedEmployeeDto) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new EmployeeNotFoundException(
                        messageService.getMessage("employee.notfound", employeeId)
                ));

        employee.setFirstName(updatedEmployeeDto.getFirstName());
        employee.setLastName(updatedEmployeeDto.getLastName());
        employee.setEmail(updatedEmployeeDto.getEmail());

        // Handle TodoList relationship
        if (updatedEmployeeDto.getTodoList() != null) {
            employee.setTodoList(updatedEmployeeDto.getTodoList());
        }

        // Handle designation relationship
        if (employee.getDesignation() != null) {
            Designation designation = employee.getDesignation();
            if (designation.getEmployees() == null) {
                designation.setEmployees(new ArrayList<>());
            }
            designation.getEmployees().add(employee);
        }

        // Update Projects
        List<Project> projects = updatedEmployeeDto.getProjects().stream()
                .map(projectDto -> {
                    Project project = projectRepository.findById(projectDto.getPid())
                            .orElse(new Project(projectDto.getPid(), projectDto.getProjectName(), new ArrayList<>()));
                    return project;
                })
                .collect(Collectors.toList());
        employee.setProjects(projects);

        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public void deleteEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new EmployeeNotFoundException(
                        messageService.getMessage("employee.notfound", employeeId)
                ));

        employeeRepository.deleteById(employeeId);
    }
}
