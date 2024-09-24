package net.javaguides.Employee_Management_System.mapper;

import net.javaguides.Employee_Management_System.dto.ProjectDto;
import net.javaguides.Employee_Management_System.entity.Project;

public class ProjectMapper {
    public static ProjectDto mapToProjectDto(Project project) {
        return new ProjectDto(
                project.getPid(),
                project.getProjectName(),
                project.getEmployees()
        );
    }

    public static Project mapToProject(ProjectDto projectDto) {
        return new Project(
                projectDto.getPid(),
                projectDto.getProjectName(),
                projectDto.getEmployees()
        );
    }
}
