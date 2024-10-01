package net.javaguides.Employee_Management_System.repository;

import net.javaguides.Employee_Management_System.entity.Employee;
import net.javaguides.Employee_Management_System.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
                                                    // <Type of the entity, Type of the primary key>
    // this will help us to connect Database to the repository layer
    boolean existsByEmail(String email);

    Employee findByEmail(String email);

    List<Employee> findAllByRolesContaining(Role role);
}
