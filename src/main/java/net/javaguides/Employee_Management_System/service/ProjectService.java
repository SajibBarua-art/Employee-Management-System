package net.javaguides.Employee_Management_System.service;

import net.javaguides.Employee_Management_System.dto.ProjectDto;

import java.util.List;

public interface ProjectService {
    ProjectDto getProjectById(long id);

    List<ProjectDto> getAllProjects();

    ProjectDto createProject(ProjectDto projectDto);

    ProjectDto updateProject(Long pid, ProjectDto projectDto);

    void deleteProject(long projectId);
}
