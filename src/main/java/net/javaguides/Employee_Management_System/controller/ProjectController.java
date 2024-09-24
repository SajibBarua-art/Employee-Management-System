package net.javaguides.Employee_Management_System.controller;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.ProjectDto;
import net.javaguides.Employee_Management_System.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // this class is capable to handle http request
@RequestMapping("/api/project") // to define the base url of all the rest APIs
@AllArgsConstructor
public class ProjectController {
    private ProjectService projectService;

    @GetMapping("{id}")
    ResponseEntity<?> getProjectById(@PathVariable long id){
        ProjectDto projectDto = projectService.getProjectById(id);
        return ResponseEntity.ok(projectDto);
    }
}
