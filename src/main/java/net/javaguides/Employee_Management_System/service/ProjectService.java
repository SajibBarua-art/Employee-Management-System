package net.javaguides.Employee_Management_System.service;

import net.javaguides.Employee_Management_System.dto.ProjectDto;
import net.javaguides.Employee_Management_System.entity.Project;

import java.util.List;

public interface ProjectService {
    ProjectDto getProjectById(Long id);

    List<ProjectDto> getAllProjects();

    ProjectDto createProject(Project project);

    ProjectDto updateProject(Long pid, ProjectDto projectDto);

    void deleteProject(Long projectId);
}
