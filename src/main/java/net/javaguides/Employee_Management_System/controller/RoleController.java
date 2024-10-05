package net.javaguides.Employee_Management_System.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.controller.advice.GlobalExceptionHandler;
import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import net.javaguides.Employee_Management_System.dto.RoleDto;
import net.javaguides.Employee_Management_System.entity.Role;
import net.javaguides.Employee_Management_System.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController // this class is capable to handle http request
@RequestMapping("/api/admin/roles") // to define the base url of all the rest APIs
@AllArgsConstructor
public class RoleController {
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody Role role){
        RoleDto savedRole = roleService.createRole(role);
        return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
    }

    @GetMapping("{rid}")
    public ResponseEntity<?> getRoleById(@PathVariable Long rid){
        RoleDto roleDto = roleService.getRoleById(rid);
        return ResponseEntity.ok(roleDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllRole(){
        Set<RoleDto> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PutMapping("{rid}")
    public ResponseEntity<?> updateRole(@PathVariable Long rid, @RequestBody RoleDto updatedRoleDto){
        RoleDto roleDto = roleService.updateRole(rid, updatedRoleDto);
        return ResponseEntity.ok(roleDto);
    }

    @DeleteMapping("{rid}")
    public ResponseEntity<?> deleteRole(@PathVariable Long rid){
        roleService.deleteRole(rid);
        return ResponseEntity.ok("Role deleted successfully");
    }
}
