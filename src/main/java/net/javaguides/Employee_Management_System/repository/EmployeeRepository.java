package net.javaguides.Employee_Management_System.repository;

import net.javaguides.Employee_Management_System.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
