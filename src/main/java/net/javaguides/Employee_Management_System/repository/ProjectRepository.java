package net.javaguides.Employee_Management_System.repository;

import net.javaguides.Employee_Management_System.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
