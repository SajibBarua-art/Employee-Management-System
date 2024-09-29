package net.javaguides.Employee_Management_System.repository;

import net.javaguides.Employee_Management_System.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String user);
}