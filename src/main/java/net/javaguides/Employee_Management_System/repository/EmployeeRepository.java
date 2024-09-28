package net.javaguides.Employee_Management_System.repository;

import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import net.javaguides.Employee_Management_System.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
                                                    // <Type of the entity, Type of the primary key>
    // this will help us to connect Database to the repository layer
    boolean existsByEmail(String email);

    Employee findByEmail(String email);
}
