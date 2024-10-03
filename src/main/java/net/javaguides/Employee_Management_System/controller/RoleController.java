package net.javaguides.Employee_Management_System.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.controller.advice.GlobalExceptionHandler;
import net.javaguides.Employee_Management_System.dto.RoleDto;
import net.javaguides.Employee_Management_System.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // this class is capable to handle http request
@RequestMapping("/api/admin/roles") // to define the base url of all the rest APIs
@AllArgsConstructor
public class RoleController {
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto){
        RoleDto savedRole = roleService.createRole(roleDto);
        return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
    }

    @GetMapping("{rid}")
    ResponseEntity<?> getRoleById(@PathVariable Long rid){
        RoleDto roleDto = roleService.getRoleById(rid);
        return ResponseEntity.ok(roleDto);
    }

    @GetMapping
    ResponseEntity<?> getAllRole(){
        List<RoleDto> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PutMapping("{rid}")
    ResponseEntity<?> updateRole(@PathVariable Long rid, @RequestBody RoleDto updatedRoleDto){
        RoleDto roleDto = roleService.updateRole(rid, updatedRoleDto);
        return ResponseEntity.ok(roleDto);
    }

    @DeleteMapping("{rid}")
    public ResponseEntity<?> deleteRole(@PathVariable Long rid){
        roleService.deleteRole(rid);
        return ResponseEntity.ok("Role deleted successfully");
    }
}
