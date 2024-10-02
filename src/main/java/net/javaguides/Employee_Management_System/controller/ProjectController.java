package net.javaguides.Employee_Management_System.controller;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.EmployeeDto;
import net.javaguides.Employee_Management_System.dto.ProjectDto;
import net.javaguides.Employee_Management_System.entity.Project;
import net.javaguides.Employee_Management_System.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // this class is capable to handle http request
@RequestMapping("/api/projects") // to define the base url of all the rest APIs
@AllArgsConstructor
public class ProjectController {
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody Project project){
        ProjectDto savedProject = projectService.createProject(project);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    ResponseEntity<?> getProjectById(@PathVariable Long id){
        ProjectDto projectDto = projectService.getProjectById(id);
        return ResponseEntity.ok(projectDto);
    }

    @GetMapping
    ResponseEntity<?> getAllProject(){
        List<ProjectDto> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @PutMapping("{id}")
    ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody ProjectDto updatedProjectDto){
        ProjectDto projectDto = projectService.updateProject(id, updatedProjectDto);
        return ResponseEntity.ok(projectDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
        return ResponseEntity.ok("Project deleted successfully");
    }
}
