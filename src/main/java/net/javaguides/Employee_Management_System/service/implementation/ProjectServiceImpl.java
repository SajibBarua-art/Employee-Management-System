package net.javaguides.Employee_Management_System.service.implementation;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.ProjectDto;
import net.javaguides.Employee_Management_System.entity.Employee;
import net.javaguides.Employee_Management_System.entity.Project;
import net.javaguides.Employee_Management_System.exception.ProjectNotFoundException;
import net.javaguides.Employee_Management_System.mapper.ProjectMapper;
import net.javaguides.Employee_Management_System.repository.ProjectRepository;
import net.javaguides.Employee_Management_System.service.MessageService;
import net.javaguides.Employee_Management_System.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public ProjectDto getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(
                   messageService.getMessage("project.notfound", projectId)
                ));

        return ProjectMapper.mapToProjectDto(project);
    }

    @Override
    public Set<ProjectDto> getAllProjects() {
        return projectRepository.findAll()
                .stream().map(project -> ProjectMapper.mapToProjectDto(project))
                .collect(Collectors.toSet());
    }

    @Override
    public ProjectDto createProject(Project project) {

        Project savedProject = projectRepository.save(project);

        return ProjectMapper.mapToProjectDto(savedProject);
    }

    @Override
    public ProjectDto updateProject(Long pid, ProjectDto projectDto) {
        Project project = projectRepository.findById(pid)
                .orElseThrow(() -> new ProjectNotFoundException(
                        messageService.getMessage("project.notfound", pid)
                ));

        project.setProjectName(projectDto.getProjectName());
        Project updatedProject = projectRepository.save(project);

        return ProjectMapper.mapToProjectDto(projectRepository.save(updatedProject));
    }

    @Override
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(
                        messageService.getMessage("project.notfound", projectId)
                ));

        project.removeAllEmployees();

        projectRepository.delete(project);
    }
}