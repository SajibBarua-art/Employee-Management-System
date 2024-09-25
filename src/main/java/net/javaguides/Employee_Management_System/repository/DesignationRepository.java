package net.javaguides.Employee_Management_System.repository;

import net.javaguides.Employee_Management_System.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignationRepository extends JpaRepository<Designation, Long> {
}
